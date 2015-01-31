package org.josescalia.rss.dao.impl;

import org.josescalia.common.dao.impl.CommonDaoImpl;
import org.josescalia.rss.dao.RssDao;
import org.josescalia.rss.model.Rss;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: Josescalia
 * Date: 8/18/13
 * Time: 11:37 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class RssDaoImpl extends CommonDaoImpl<Rss, Long> implements RssDao {
    public RssDaoImpl() {
        setClazz(Rss.class);
    }
}
