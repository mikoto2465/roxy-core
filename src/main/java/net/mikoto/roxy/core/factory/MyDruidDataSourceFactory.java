package net.mikoto.roxy.core.factory;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author mikoto
 * @date 2022/12/30
 * Create for core
 */
public class MyDruidDataSourceFactory implements DataSourceFactory {
    private DataSource dataSource;

    @Override
    public void setProperties(Properties props) {
        try {
            this.dataSource = DruidDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }
}
