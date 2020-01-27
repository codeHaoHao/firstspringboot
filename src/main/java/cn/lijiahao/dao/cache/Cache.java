package cn.lijiahao.dao.cache;

import cn.lijiahao.po.BaseBean;

public interface Cache<T extends BaseBean> {
    T get(int id);
    void set(T bean);
    void remove(int id);
}
