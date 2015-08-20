package br.feevale.labex.service;

import java.util.List;

/**
 * Created by 0126128 on 08/12/2014.
 */
public interface BaseService<T> {
    public T save(T entity);
    public void delete(Long id);
    public T findById(Long id);
    public List<T> findAll();
    public List<T> findByParam(Object... params);
}
