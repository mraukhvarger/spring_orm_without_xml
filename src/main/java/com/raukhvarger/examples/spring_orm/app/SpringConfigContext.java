package com.raukhvarger.examples.spring_orm.app;

import com.raukhvarger.examples.spring_orm.db.DAO;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by jerde on 03.11.2016.
 */

@Configuration
@EnableTransactionManagement
public class SpringConfigContext {

    @Bean
    App mockApp() { return new App(); }

    @Bean
    public DataSource mockDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.HSQL)
                .build();
        return db;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mockEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(mockDataSource());
        entityManager.setPersistenceUnitName("myPersistence");
        entityManager.setPersistenceProvider(new HibernatePersistenceProvider());
        entityManager.setPackagesToScan("com.raukhvarger.examples.spring_orm.db.entity");
        HibernateJpaVendorAdapter hibJpa = new HibernateJpaVendorAdapter();
        hibJpa.setGenerateDdl(true);
        hibJpa.setShowSql(true);
        hibJpa.setDatabase(Database.HSQL);
        entityManager.setJpaVendorAdapter(hibJpa);
        return entityManager;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        transactionManager.setDataSource(mockDataSource());
        transactionManager.setJpaDialect(new HibernateJpaDialect());
        return transactionManager;
    }

    @Bean
    public DAO mockDAO() {
        return new DAO();
    }

}
