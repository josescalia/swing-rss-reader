package org.josescalia.swingrss.service.impl;

import org.josescalia.rss.dao.ApplicationConfigDao;
import org.josescalia.rss.model.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by josescalia on 31/01/15.
 */
@Service
public class ApplicationConfigServiceImpl {
    
    @Autowired
    private ApplicationConfigDao dao;
    
    @Transactional(readOnly = true)
    public List<ApplicationConfig> getAll(){
        return dao.getAll();
        
    }

    @Transactional
    public ApplicationConfig save(ApplicationConfig config) throws Exception{
        return dao.save(config);
    }

    @Transactional(readOnly = true)
    public List<ApplicationConfig> findExact(Map<String,Object> param) throws Exception {
        return dao.findExact(param);
        
    }
    
    @Transactional(readOnly = true)
    public int getNum(Map<String,Object> param) throws Exception{
        return dao.getTotalRow(param);
        
    }
}
