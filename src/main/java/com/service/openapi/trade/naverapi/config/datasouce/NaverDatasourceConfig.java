package com.service.openapi.trade.naverapi.config.datasouce;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ConfigurationProperties(prefix = "datasource.naver")
@EnableJpaRepositories(
        basePackages = {"com.service.openapi.trade.naverapi.repository.naver"},
        transactionManagerRef = "transactionManagerNaver",
        entityManagerFactoryRef = "entityManagerFactoryNaver"
)
public class NaverDatasourceConfig extends HikariConfig {
    @Bean
    public PlatformTransactionManager transactionManagerNaver() {
        return new JpaTransactionManager(this.entityManagerFactoryNaver());
    }

    @Bean
    public DataSource dataSourceNaver() {
        return new LazyConnectionDataSourceProxy(new HikariDataSource(this));
    }

    @Bean
    public EntityManagerFactory entityManagerFactoryNaver() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(this.dataSourceNaver());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        factoryBean.setJpaProperties(this.properties());
        factoryBean.setPackagesToScan("com.service.openapi.trade.naverapi.entity.naver");
        factoryBean.setPersistenceUnitName("naver");
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    private Properties properties() {
        final Properties properties = new Properties();
        properties.put("hibernate.hdm2ddl.auto", "none");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.put("hibernate.show_sql", "true");

        return properties;
    }
}
