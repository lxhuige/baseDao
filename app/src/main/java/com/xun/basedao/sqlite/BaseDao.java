package com.xun.basedao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.xun.basedao.annotation.DbTable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseDao<T> implements IBaseDao<T> {

    private Class<T> entityClass;

    private SQLiteDatabase sqLiteDatabase;
    //表名
    private String tableName;

    private Set<String> cacheField = new HashSet<>();

    private boolean isInit = false;

    void init(Class<T> entityClass, SQLiteDatabase sqLiteDatabase, int version) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.entityClass = entityClass;
        //如果ssqLiteDatabase没有打开或已经初始化过直接返回
        if (sqLiteDatabase.isOpen() && !isInit) {
            int databaseVersion = sqLiteDatabase.getVersion();
            String slq = getCreateSlq(entityClass);
            if (databaseVersion < version) {
                try {
                    sqLiteDatabase.execSQL("drop table " + tableName);
                }catch (Exception e){
                    e.printStackTrace();
                }
                sqLiteDatabase.execSQL(slq);
                sqLiteDatabase.setVersion(version);
            }
            isInit = true;
        }
    }

    @Override
    public long insert(T entity) {
        return sqLiteDatabase.insert(tableName, null, getValues(entity));
    }

    @Override
    public List<T> queryAll() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        String sql = "select * from " + tableName;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        List<T> list = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            list.add(initResult(cursor));
        }
        cursor.close();
        return list;
    }

    @Override
    public List<T> queryListByPage(String where, String orderBy, int page, int number) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        if (TextUtils.isEmpty(where) && TextUtils.isEmpty(orderBy)) {
            return queryListByPage(page, number);
        }
        if (TextUtils.isEmpty(orderBy))
            orderBy = " order by _id ";
        String sql = "select * from " + tableName + where + orderBy + " asc limit ? Offset ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(number), String.valueOf(((page - 1) * number))});
        List<T> list = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            list.add(initResult(cursor));
        }
        cursor.close();
        return list;
    }

    @Override
    public List<T> queryListByPage(int page, int number) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        String sql = "select * from " + tableName + " order by _id asc limit ? Offset ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(number), String.valueOf(((page - 1) * number))});
        List<T> list = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            list.add(initResult(cursor));
        }
        cursor.close();
        return list;
    }

    @Override
    public List<T> queryList(String where) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        String sql = "select * from " + tableName + where;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        List<T> list = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            list.add(initResult(cursor));
        }
        cursor.close();
        return list;
    }

    @Override
    public T queryById(int id) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        String sql = "select * from " + tableName + " where _id = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.getCount() <= 0) {
            return null;
        }
        T instance = null;
        while (cursor.moveToNext()) {
            instance = initResult(cursor);
        }
        cursor.close();
        return instance;
    }

    @Override
    public int update(T entity) throws NoSuchFieldException, IllegalAccessException {
        Field fieldId = entityClass.getDeclaredField("_id");
        fieldId.setAccessible(true);
        Integer id = (Integer) fieldId.get(entity);
        if (id == 0) {
            throw new IllegalAccessException("id不能为零");
        }
        return sqLiteDatabase.update(tableName, getValues(entity), "_id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public int delete(T entity) throws NoSuchFieldException, IllegalAccessException {
        Field fieldId = entityClass.getDeclaredField("_id");
        fieldId.setAccessible(true);
        Integer id = (Integer) fieldId.get(entity);
        if (id == 0) {
            throw new IllegalAccessException("id不能为零");
        }
        return deleteById(id);
    }

    @Override
    public int deleteById(int id) {
        return sqLiteDatabase.delete(tableName, "_id = ?", new String[]{String.valueOf(id)});
    }

    /**
     * @param cursor 根据cursor分装对象
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     */
    private T initResult(Cursor cursor) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        T instance = entityClass.newInstance();
        for (String fieldName : cacheField) {
            int columnIndex = cursor.getColumnIndex(fieldName);
            Field field = entityClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            Class<?> type = field.getType();
            if (type == String.class) {
                String string = cursor.getString(columnIndex);
                field.set(instance, string);
            } else if (type == Integer.class) {
                int anInt = cursor.getInt(columnIndex);
                field.set(instance, anInt);
            } else if (type == Double.class) {
                Double anInt = cursor.getDouble(columnIndex);
                field.set(instance, anInt);
            } else if (type == Long.class) {
                Long anInt = cursor.getLong(columnIndex);
                field.set(instance, anInt);
            } else if (type == byte[].class) {
                byte[] anInt = cursor.getBlob(columnIndex);
                field.set(instance, anInt);
            }
        }
        return instance;
    }

    private ContentValues getValues(T entity) {
        ContentValues values = new ContentValues();
        for (String fieldName : cacheField) {
            try {
                Field field = entityClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object object = field.get(entity);
                if (object == null) {
                    continue;
                }
                Class<?> type = field.getType();
                if (type == String.class) {
                    values.put(fieldName, object.toString());
                } else if (type == Integer.class) {
                    values.put(fieldName, (Integer) object);
                } else if (type == Double.class) {
                    values.put(fieldName, (Double) object);
                } else if (type == Long.class) {
                    values.put(fieldName, (Long) object);
                } else if (type == byte[].class) {
                    values.put(fieldName, (byte[]) object);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return values;
    }

    /**
     * @param entityClass 需要操作的对象字节码
     * @return 拼接好的sql语句
     */
    private String getCreateSlq(Class<T> entityClass) {
        StringBuffer sb = new StringBuffer();
        //如果表不存在则创建新表
        sb.append("CREATE TABLE IF NOT EXISTS ");
        tableName = entityClass.getAnnotation(DbTable.class).value();
        if (TextUtils.isEmpty(tableName)) {
            throw new RuntimeException("表名必须添加注解");
        }
        sb.append(tableName);
        sb.append(" (");
        //主键值增长
        sb.append("_id INTEGER PRIMARY KEY AUTOINCREMENT ,");
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            cacheField.add(field.getName());
            //如果等于id 则下一个
            if (field.getName().equals("_id")) {
                continue;
            }
            Class<?> type = field.getType();
            if (type == String.class) {
                sb.append(field.getName());
                sb.append(" TEXT ,");
            } else if (type == Integer.class) {
                sb.append(field.getName());
                sb.append(" INTEGER ,");
            } else if (type == Double.class) {
                sb.append(field.getName());
                sb.append(" DOUBLE ,");
            } else if (type == Long.class) {
                sb.append(field.getName());
                sb.append(" BIGINT ,");
            } else if (type == byte[].class) {
                sb.append(field.getName());
                sb.append(" BLOB ,");
            }
        }

        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(")");
        return sb.toString();
    }

}
