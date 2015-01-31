/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.josescalia.common.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.josescalia.common.dao.CommonDao;
import org.josescalia.common.dao.model.AuditableBase;
import org.josescalia.common.dao.model.ReferenceBase;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Use this class by extending it to dao layer class of the domain, and then
 * create a constructor and call setClass method inside the constructor<br>
 * eg:<br>
 * <pre>
 * &#64;Repository
 * public class AuthorDaoImpl extends GenericDaoImpl<Author, Long> implements AuthorDao {
 * public AuthorDaoImpl() {
 * setClazz(Author.class);
 * }
 * }
 * </pre>
 */
public class CommonDaoImpl<T, PK> implements CommonDao<T, PK> {
    
    private Logger logger = Logger.getLogger(CommonDaoImpl.class.getName());
    private Class<T> clazz;

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Method to set Domain class
     *
     * @param clazzToSet entity class
     */
    public void setClazz(final Class<T> clazzToSet) {
        clazz = clazzToSet;
    }

    /**
     * Method to get hibernate current session
     */
    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Private method to update the data in database<br>
     * The method specific set the version incrementally changed, the class
     * which use this method must extend Auditable class
     *
     * @param obj instance of extended Auditable Class
     * @return T an object its own after some validation by fetching the
     * database
     */
    private T update(ReferenceBase obj) throws Exception {
        T temp = getById((PK) obj.getId());
        if (temp != null) {
            BeanUtils.copyProperties(obj, temp);
            try {
                getCurrentSession().update(temp);
                return temp;
            } catch (Exception e) {
                logger.error("Exception : " + e.getMessage());
                throw new Exception(e);
            }
        } else {
            logger.info("Class " + obj.getClass().getName() + " is not extend ReferenceBase class, cannot use Common saveOrUpdate method");
            return null;
        }
    }

    /**
     * Private method to update the data in database<br>
     * The method spesific set the version incrementally changed, the class
     * which use this method must extend Auditable class
     *
     * @param obj instance of extended Auditable Class
     * @return T an object its own after some validation by fetching the
     * database
     */
    private T update(AuditableBase obj) throws Exception {
        T temp = getById((PK) obj.getId());
        if (temp != null) {
            BeanUtils.copyProperties(obj, temp);
            try {
                getCurrentSession().update(temp);
                return temp;
            } catch (Exception e) {
                logger.error("Exception : " + e.getMessage());
                //return null;
                throw new Exception(e);
            }

        } else {
            logger.info("Class " + obj.getClass().getName() + " is not extend Auditable class, cannot use Common saveOrUpdate method");
            return null;
        }
    }

    /**
     * Method to get single object in database using domain class primary key as
     * a parameter
     *
     * @param id primary key of serialization class
     * @return T Object of the entity
     */
    @Override
    public T getById(PK id) {
        return (T) getCurrentSession().get(clazz, (Serializable) id);
    }

    /**
     * Method to get all entity data from database
     *
     * @return ArrayList of entity
     */
    @Override
    public List<T> getAll() {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        if (t instanceof ReferenceBase) {
            Criteria criteria = getCurrentSession().createCriteria(t.getClass());
            criteria.add(Restrictions.eq("deleted", 0));
            return criteria.list();
        } else if (t instanceof AuditableBase) {
            Criteria criteria = getCurrentSession().createCriteria(t.getClass());
            return criteria.list();
        } else {
            return (List<T>) getCurrentSession().createQuery("from " + clazz.getName()).list();
        }
    }

    /**
     * Method to get list of entity data from database. The list can be filtered
     * with parameter mapParam. Used to fetch pagination style
     *
     * @param mapParam - a filtered Map
     * @param startRow - int of start row
     * @param lengthRow - int of how much row to fetch
     * @return array of entity List
     */
    public List<T> getPaginatedList(Map<String, Object> mapParam, int startRow, int lengthRow) throws Exception {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new Exception(e);
        } catch (IllegalAccessException e) {
            throw new Exception(e);
        }

        List<T> rList = new ArrayList<T>();
        String field = "";
        Object value = "";

        Criteria criteria = getCurrentSession().createCriteria(t.getClass());

        if (mapParam != null) {

            field = mapParam.get("searchCat").toString();
            value = mapParam.get("searchVal");
            if (value instanceof String) {
                criteria.add(Restrictions.ilike(field, String.valueOf(value), MatchMode.ANYWHERE));
            } else {
                criteria.add(Restrictions.like(field, value));
            }
        }
        //paging
        criteria.setFirstResult(startRow);
        criteria.setMaxResults(lengthRow);
        if (t instanceof ReferenceBase) {
            criteria.add(Restrictions.eq("deleted", 0));
            criteria.addOrder(Order.asc("id"));
        }

        try {
            rList = criteria.list();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return rList;
    }

    public List<T> getDataTablePaginatedList(Map<String, Object> mapParam, int startRow, int lengthRow) throws Exception {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new Exception(e);
        } catch (IllegalAccessException e) {
            throw new Exception(e);
        }

        List<T> rList = new ArrayList<T>();
        Criteria criteria = getCurrentSession().createCriteria(t.getClass());

        if (mapParam != null) {
            Disjunction or = Restrictions.disjunction();
            for (Object key : mapParam.keySet()) {
                logger.info(key + " : " + mapParam.get(key));
                String field = key.toString();
                Object value = mapParam.get(key);
                //must filter by id
                if (field.equalsIgnoreCase("id")) {
                    try {
                        or.add(Restrictions.eq(field, new Long(value.toString())));
                    } catch (Exception e) {
                        logger.error("Cannot cast " + value + " to Long data type");
                    }
                } else if (value instanceof String) {
                    or.add(Restrictions.ilike(field, value.toString(), MatchMode.ANYWHERE));
                } else {
                    or.add(Restrictions.like(field, value));
                }

                //or.add(Restrictions.ilike(String.valueOf(key), mapParam.get(key).toString(), MatchMode.ANYWHERE));
                criteria.add(or);
            }

        }
        logger.info("Start Row " + startRow);
        logger.info("Length Row " + lengthRow);
        //paging
        criteria.setFirstResult(startRow);
        criteria.setMaxResults(lengthRow);
        if (t instanceof ReferenceBase) {
            criteria.add(Restrictions.eq("deleted", 0));
            criteria.addOrder(Order.asc("id"));
        }

        try {
            logger.info(criteria.toString());
            rList = criteria.list();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return rList;
    }

    /**
     * Method to get list of entity data from database. The list can be filtered
     * with parameter mapParam. Used to fetch pagination style
     *
     * @param mapParam - a filtered Map
     * @return array of entity List
     */
    public List<T> getFilteredList(Map<String, Object> mapParam) throws Exception {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new Exception(e);
        } catch (IllegalAccessException e) {
            throw new Exception(e);
        }

        List<T> rList = new ArrayList<T>();
        String field = "";
        Object value = "";

        Criteria criteria = getCurrentSession().createCriteria(t.getClass());

        if (mapParam != null) {

            field = mapParam.get("searchCat").toString();
            value = mapParam.get("searchVal");
            if (value instanceof String) {
                criteria.add(Restrictions.ilike(field, String.valueOf(value), MatchMode.ANYWHERE));
            } else {
                criteria.add(Restrictions.like(field, value));
            }
        }
        //paging
        if (t instanceof ReferenceBase) {
            criteria.add(Restrictions.eq("deleted", 0));
            criteria.addOrder(Order.asc("id"));
        }

        try {
            rList = criteria.list();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return rList;
    }

    /**
     * Method to Save an object to database
     *
     * @param obj entity
     * @return T current entity
     */
    @Override
    public T save(T obj) throws Exception {
        if (obj != null) {
            //System.out.println("domain " + obj.toString());
            if (obj instanceof ReferenceBase) { // ReferenceBase with version and deleted
                if (((ReferenceBase) obj).getId() != null) {
                    return update((ReferenceBase) obj);
                } else {
                    ((ReferenceBase) obj).setVersion(0);
                    ((ReferenceBase) obj).setCreatedDate(new Date());   //audit rail capability
                    ((ReferenceBase) obj).setUpdatedDate(new Date());   //audit rail capability
                    getCurrentSession().save(obj);
                    return obj;
                }
            } else if (obj instanceof AuditableBase) {  //AuditableBaseDTO only with auditrail capability
                if (((AuditableBase) obj).getId() != null) {
                    return update((AuditableBase) obj);
                } else {
                    ((AuditableBase) obj).setCreatedDate(new Date());
                    ((AuditableBase) obj).setUpdatedDate(new Date());
                    getCurrentSession().save(obj);
                    return obj;
                }
            } else {
                logger.debug("domain is Entity Base");
                try {
                    getCurrentSession().saveOrUpdate(obj);
                    return obj;
                } catch (Exception e) {
                    logger.error("Exception : " + e.getMessage());
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    /**
     * Method to delete a data from table using entity primary key as parameter
     *
     * @param id primary key of serialization class
     * @return boolean
     */
    @Override
    public boolean delete(PK id) throws Exception {
        T t = getById(id);
        if (t != null) {
            if (t instanceof ReferenceBase) {
                return softDelete(id);
            } else {
                try {
                    getCurrentSession().delete(t);
                    return true;
                } catch (Exception e) {
                    logger.error("Exception : " + e.getMessage());
                    throw new Exception(e);
                }
            }
        } else {
            return false;
        }
    }

    /**
     * This method is not deleting the data from database,<br>
     * it only set the flag deleted to false and upgrade the field version on
     * database incrementally<br>
     * The usage of this method, an object must extend Auditable,
     *
     * @param id primary key of serialization class
     * @return boolean
     */
    public boolean softDelete(PK id) throws Exception {
        T t = getById(id);
        if (t instanceof ReferenceBase) {
            ((ReferenceBase) t).setDeleted(1);
            ((ReferenceBase) t).setUpdatedDate(new Date());
            try {
                getCurrentSession().update(t);
                return true;
            } catch (Exception e) {
                logger.error("Exception : " + e.getMessage());
                throw new Exception(e);
            }
        }/* else if (t instanceof AuditableBase) {
         ((AuditableBase) t).setUpdatedDate(new Date());
         try {
         getCurrentSession().update(t);
         return true;
         } catch (Exception e) {
         logger.error("Exception : " + e.getMessage());
         throw new Exception(e);
         }
         }*/

        logger.info("Cannot use softDelete function, class " + t.getClass().getName() + "is not extend Auditable class");
        return false;
    }

    /**
     * To get Total Count of a table
     *
     * @param mapParam Map
     * @return totalRow by filtered with params
     */
    @Override
    public int getTotalRow(Map<String, Object> mapParam) throws Exception {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new Exception(e);
        } catch (IllegalAccessException e) {
            throw new Exception(e);
        }

        int iResult;
        String sQuery = " from " + clazz.getName() + " ";
        if (t instanceof ReferenceBase) {
            sQuery += " WHERE deleted=0";
        }

        if (mapParam != null) {
            String field = mapParam.get("searchCat").toString();
            Object value = mapParam.get("searchVal");
            sQuery += " WHERE ";
            if (t instanceof ReferenceBase) {
                sQuery += " AND ";
            }
            sQuery += field + " like " + "'%" + value + "%'";
        }
        //System.out.println(sQuery);
        try {
            iResult = getCurrentSession().createQuery(sQuery).list().size();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return iResult;
    }

    /**
     * Method to use custom hibernate query to do database operational
     *
     * @param HQL string of query
     * @return array list of entity
     */
    public List<T> executeQuery(String HQL) {
        //T t = null;
        Query query = getCurrentSession().createQuery(HQL);
        return query.list();
    }

    /**
     * This method is used to find list of object in the database using paired
     * field and value
     *
     * @param paramFieldValue a paired field and value to search
     * @return List of object
     *
     */
    @Override
    public List<T> findExact(Map<String, Object> paramFieldValue) throws Exception {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new Exception(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new Exception(e.getMessage());
        }
        List<T> rList = new ArrayList<T>();
        String field = "";
        Object value = null;
        for (Object key : paramFieldValue.keySet()) {
            field = (String) key.toString();
            value = paramFieldValue.get(field);
        }
        String query = "from " + clazz.getName() + " where ";
        Criteria criteria = getCurrentSession().createCriteria(t.getClass());
        if (value instanceof String) {
            criteria.add(Restrictions.eq(field, String.valueOf(value)));
        } else {
            criteria.add(Restrictions.eq(field, value));
        }

        try {
            rList = criteria.list();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return rList;
    }
    
    
}
