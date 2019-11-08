package cn.lijiahao.dao;

public interface Dao<T> {
    T get(int id);
    int remove(int id);
}
