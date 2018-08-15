package com.xun.basedao.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.HashMap;


public class BaseDaoFactory {
    private static final BaseDaoFactory ourInstance = new BaseDaoFactory();

    public static BaseDaoFactory getInstance() {
        return ourInstance;
    }

    private BaseDaoFactory() {
    }

    private SQLiteDatabase sqLiteDatabase;
    private int version;

    private HashMap<String, BaseDao> cacheMap;

    /**
     * @param context context
     * @param version 版本号 升级会删除之前的
     */
    public void init(Context context, int version) {
        this.version = version;
        String sqlPath = "data/data/" + context.getPackageName() + "/huige.db";
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqlPath, null);
        cacheMap = new HashMap<>();
    }

    @Nullable
    public <T> BaseDao<T> getBaseDao(Class<T> entity) {
        if (sqLiteDatabase == null) {
            throw new RuntimeException("请进行初始化");
        }
        if (cacheMap.containsKey(entity.getCanonicalName())) {
            return (BaseDao<T>) cacheMap.get(entity.getCanonicalName());

        }
        BaseDao<T> baseDao = null;
        try {
            baseDao = BaseDao.class.newInstance();
            if (null != baseDao) {
                baseDao.init(entity, sqLiteDatabase, version);
                cacheMap.put(entity.getCanonicalName(), baseDao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseDao;
    }
}
