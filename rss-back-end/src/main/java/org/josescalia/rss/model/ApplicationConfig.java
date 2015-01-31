package org.josescalia.rss.model;

import org.josescalia.common.dao.model.EntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by josescalia on 31/01/15.
 */
@Entity
@Table(name = "app_config")
public class ApplicationConfig  extends EntityBase{
    private String configName;
    private String configValue;

    public ApplicationConfig() {
    }

    
    
    public ApplicationConfig(String configName, String configValue) {
        this.configName = configName;
        this.configValue = configValue;
    }

    @Column(name = "CONFIG_NAME", length = 100, nullable = false,unique = true)
    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    @Column(name = "CONFIG_VALUE", length = 100, nullable = false)
    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    @Override
    public String toString() {
        return "ApplicationConfig{" +
                "configName='" + configName + '\'' +
                ", configValue='" + configValue + '\'' +
                '}';
    }
}
