package com.hossain.database.bean;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;

@Remote
public interface DatabaseService {
    <T> T save(T entity);
    <T> T update(T entity);
    <T> void delete(Integer id, Class<T> entityClass);
    <T> T findByFieldName(Class<T> tClass, String fieldName, Object value);
    <T> List<T> findByQuery(String sql, Class<T> entityClass, Map<String, Object> params, int start, int limit);
    int findCount(String sql, Map<String, Object> params);
    boolean emailTaken(String email);
    boolean phoneTaken(String phoneNumber);
}
