package org.josescalia.swingrss.service.impl;

import org.josescalia.rss.dao.RssDao;
import org.josescalia.rss.model.Rss;
import org.josescalia.swingrss.service.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Josescalia
 * Date: 8/18/13
 * Time: 11:38 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RssServiceImpl implements RssService {

    @Autowired
    private RssDao dao;

    @Transactional(readOnly = true)
    public int getTotalRow(Map<String, Object> map) throws Exception {
        return dao.getTotalRow(map);
    }

    @Transactional(readOnly = true)
    public List<Rss> getList(Map<String, Object> map, int i, int i2) throws Exception {
        return dao.getPaginatedList(map,i,i2);
    }

    @Transactional(readOnly = true)
    public Rss getById(Long aLong) {
        return dao.getById(aLong);
    }

    @Transactional(readOnly = false)
    public Rss save(Rss rss) {
        return dao.save(rss);
    }

    @Transactional(readOnly = false)
    public boolean delete(Long aLong) {
        return dao.delete(aLong);
    }

    @Transactional(readOnly = true)
    public List<Rss> getAll() {
        return dao.getAll();
    }
}
