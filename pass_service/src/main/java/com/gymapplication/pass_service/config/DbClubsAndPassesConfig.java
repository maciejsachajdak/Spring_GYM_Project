package com.gymapplication.pass_service.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "clubsAndPassesEntityManagerFactory",
        basePackages = {"com.gymapplication.pass_service.repositoryDbClubsAndPasses"},
        transactionManagerRef = "clubsAndPassesTransactionManager"
)
@EntityScan(basePackages = {"com.gymapplication.pass_service.entityDbClubsAndPasses"})
public class DbClubsAndPassesConfig {

    @Autowired
    Environment env;

    @Primary
    @Bean(name= "clubsAndPassesDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(env.getProperty("clubsAndPasses.datasource.url"));
        ds.setUsername(env.getProperty("clubsAndPasses.datasource.username"));
        ds.setPassword(env.getProperty("clubsAndPasses.datasource.password"));
        ds.setDriverClassName(env.getProperty("clubsAndPasses.datasource.driver-class-name"));
        return ds;
    }


    @Primary
    @Bean(name= "clubsAndPassesEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);
        HashMap<String,Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        bean.setJpaPropertyMap(properties);
        bean.setPackagesToScan("com.gymapplication.pass_service.entityDbClubsAndPasses");
        return bean;

    }

    @Primary
    @Bean("clubsAndPassesTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("clubsAndPassesEntityManagerFactory") EntityManagerFactory entityManagerFactory ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
