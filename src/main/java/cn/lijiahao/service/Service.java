package cn.lijiahao.service;

import cn.lijiahao.po.BaseBean;

public interface Service<T extends BaseBean> {
    T get(int id);
    int remove(int id);
}
