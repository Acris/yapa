package com.reorz.yapa.config;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * 配置文件对应实体类
 *
 * @author Acris
 */
@JsonRootName("yapa")
public class ConfigurationProperties {
    @JsonAlias({"datasource", "dataSource", "data-source"})
    private DataSource dataSource;
    private Mappers mappers;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Mappers getMappers() {
        return mappers;
    }

    public void setMappers(Mappers mappers) {
        this.mappers = mappers;
    }

    public static class DataSource {
        private String driver;
        private String url;
        private String username;
        private String password;
        // Getter and Setters

        public String getDriver() {
            return driver;
        }

        public void setDriver(String driver) {
            this.driver = driver;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class Mappers {
        @JsonAlias({"basepackage", "basePackage", "base-package"})
        private String basePackage;

        public String getBasePackage() {
            return basePackage;
        }

        public void setBasePackage(String basePackage) {
            this.basePackage = basePackage;
        }
    }
}
