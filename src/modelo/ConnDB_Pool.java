/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author diego
 */
public class ConnDB_Pool {

    private static final HikariConfig config;
    private static final HikariDataSource ds;
 
    static {
        config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost/DBpf");
        config.setUsername("app_pf");
        config.setPassword("asdf");
        config.setMaximumPoolSize(12);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }
 
    public ConnDB_Pool() {
    }
 
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }  
}
