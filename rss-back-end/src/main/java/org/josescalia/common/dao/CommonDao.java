/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.josescalia.common.dao;

import java.util.List;
import java.util.Map;

/**
 *
 * @author josescalia
 */
/**
 * Common dao is an generic interface class.<br>
 * Used to create a standard CRUD methods on a domain class in the applications.
 *
 */
public interface CommonDao<T,PK> {
    int getTotalRow(Map<String, Object> mapParam) throws Exception;

    List<T> getPaginatedList(Map<String, Object> mapParam, int startRow, int lengthRow) throws Exception;

    List<T> getDataTablePaginatedList(Map<String, Object> mapParam, int startRow, int lengthRow) throws Exception;

    List<T> getFilteredList(Map<String, Object> mapParam) throws Exception;

    T getById(final PK id);

    T save(final T entity) throws Exception;

    boolean delete(PK id) throws Exception;

    List<T> getAll();

    List<T> executeQuery(String hql);

    List<T> findExact(Map<String, Object> paramFieldValue) throws Exception;

}
