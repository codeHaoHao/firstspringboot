package cn.lijiahao.service;

import cn.lijiahao.po.BaseBean;

public interface Service<T extends BaseBean> {
	void setCache(T t);
    T get(int id);
    int remove(int id);
}
