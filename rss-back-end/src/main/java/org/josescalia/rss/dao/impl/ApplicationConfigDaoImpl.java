package org.josescalia.rss.dao.impl;

import org.josescalia.common.dao.impl.CommonDaoImpl;
import org.josescalia.rss.dao.ApplicationConfigDao;
import org.josescalia.rss.model.ApplicationConfig;
import org.springframework.stereotype.Repository;

/**
 * Created by josescalia on 31/01/15.
 */
@Repository
public class ApplicationConfigDaoImpl extends CommonDaoImpl<ApplicationConfig, Long> implements ApplicationConfigDao {
    public ApplicationConfigDaoImpl() {
        setClazz(ApplicationConfig.class);
    }
}
