package com.hossain.database.bean;

import com.hossain.database.model.Employee;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Remote(DatabaseService.class)
@Stateless(name = "DatabaseServiceEJB", mappedName = "simple-curd-DatabaseServiceBean")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DatabaseServiceBean implements DatabaseService {

    @PersistenceContext(unitName = "CURD")
    private EntityManager em;

    @Override
    public <T> T save(T entity) {
        System.out.println("Entity Manager:"+em);
        em.clear();
        em.persist(entity);
        em.flush();
        return entity;
    }

    @Override
    public <T> T update(T entity) {
        System.out.println("Entity Manager:"+em);
        em.clear();
        em.merge(entity);
        em.flush();
        return entity;
    }

    @Override
    public <T> void delete(Integer id, Class<T> entityClass) {
        Query query = em.createQuery("delete from "+entityClass.getSimpleName()+" o where o.id=:id").setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public <T> T findByFieldName(Class<T> tClass, String fieldName, Object value) {
        Query query = em.createQuery("select o from "+tClass.getSimpleName()+" o where o."+fieldName+"=:value", tClass);
        query.setParameter("value", value);
        try {
            return (T)query.getSingleResult();
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> List<T> findByQuery(String sql, Class<T> entityClass, Map<String, Object> params, int start, int limit) {
        Query query = em.createQuery(sql, entityClass).setFirstResult(start).setMaxResults(limit);
        if(params != null && params.size() >0) {
            params.forEach((k,v)->query.setParameter(k,v));
        }
        return (List<T>)query.getResultList();
    }

    @Override
    public int findCount(String sql, Map<String, Object> params) {
        Query query = em.createQuery(sql);
        if(params != null && params.size() >0) {
            params.forEach((k,v)->query.setParameter(k,v));
        }
        Object value = query.getSingleResult();
        if(value instanceof BigDecimal) {
            return ((BigDecimal) value).intValue();
        }
        if(value instanceof BigInteger) {
            return ((BigInteger)value).intValue();
        }
        if(value instanceof Long) {
            return ((Long)value).intValue();
        }
        if(value instanceof Integer) {
            return ((Integer)value).intValue();
        }
        return 0;
    }

    @Override
    public boolean emailTaken(String email) {
        Employee employee = this.findByFieldName(Employee.class, "email", email);
        return employee != null && employee.getId() != null;
    }

    @Override
    public boolean phoneTaken(String phoneNumber) {
        Employee employee = this.findByFieldName(Employee.class, "phoneNumber", phoneNumber);
        return employee != null && employee.getId() != null;
    }
}
