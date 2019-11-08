package cn.lijiahao.serviceImpl;

import cn.lijiahao.dao.Dao;
import cn.lijiahao.dao.cache.Cache;
import cn.lijiahao.po.BaseBean;
import org.springframework.beans.factory.InitializingBean;

public abstract class GenericServiceImpl<T extends BaseBean> implements InitializingBean {
    private Cache<T> cache;
    private Dao<T> dao;

    /**
     * 子类实现用于向父类注入cache和dao，此方法是InitializingBean的方法
     * @throws Exception
     */
    public abstract void  afterPropertiesSet() throws Exception;

    public T get(int id){
        T t = cache.get(id);
        if (t==null){
            t = dao.get(id);
            cache.set(t);
        }
        return t;
    }

    public int remove(int id){
        int r = dao.remove(id);
        cache.remove(id);
        return r;
    }

    public void setCache(Cache<T> cache) {
        this.cache = cache;
    }

    public void setDao(Dao<T> dao) {
        this.dao = dao;
    }

}
