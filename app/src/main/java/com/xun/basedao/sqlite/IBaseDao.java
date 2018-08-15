package com.xun.basedao.sqlite;

import java.util.List;

public interface IBaseDao<T> {

    /**
     * 插入数据
     */
    long insert(T entity);

    /**
     * @return 查询所有
     */
    List<T> queryAll() throws NoSuchFieldException, IllegalAccessException, InstantiationException;

    /**
     * @param id 根据id 查询
     */
    T queryById(int id) throws IllegalAccessException, InstantiationException, NoSuchFieldException;

    /**
     * @param entity 需要带id的对象
     * @return the number of rows affected
     */
    int update(T entity) throws NoSuchFieldException, IllegalAccessException;

    /**
     * @param entity 需要带id的对象
     * @return the number of rows affected
     */
    int delete(T entity) throws NoSuchFieldException, IllegalAccessException;

    /**
     * @param id 根据id 查询
     * @return the number of rows affected
     */
    int deleteById(int id);

    /**
     * @param where   查询条件 可以为null
     * @param orderBy 排序 默认id正序
     * @param page    页数
     * @param number  每页取的数据
     * @return 所查询数据
     */
    List<T> queryListByPage(String where, String orderBy, int page, int number) throws IllegalAccessException, NoSuchFieldException, InstantiationException;

    /**
     * 无条件查询
     *
     * @param page   页数
     * @param number 每页取的数据
     * @return 所查询数据
     */
    List<T> queryListByPage(int page, int number) throws IllegalAccessException, NoSuchFieldException, InstantiationException;

    /**
     * @param where 查询条件 where _id = 1
     *              可以添加排序 order by _id
     */
    List<T> queryList(String where) throws IllegalAccessException, NoSuchFieldException, InstantiationException;

}
