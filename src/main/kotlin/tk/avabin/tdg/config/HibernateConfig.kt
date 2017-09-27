package tk.avabin.tdg.config

import org.hibernate.SessionFactory
import org.hibernate.cfg.AvailableSettings
import org.hibernate.jpa.internal.EntityManagerFactoryImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.hibernate5.HibernateTransactionManager
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import javax.sql.DataSource


/**
 * @author Avabin
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = arrayOf("tk.avabin.tdg.daos"))
class HibernateConfig {

    @Bean(name = arrayOf("sessionFactory"))
    fun getSessionFactory(): LocalSessionFactoryBean {
        val sessionFactory = LocalSessionFactoryBean()
        sessionFactory.setDataSource(getDataSource())
        val properties = Properties()
        properties.setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect")
        properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "create")
        properties.setProperty(AvailableSettings.DEFAULT_SCHEMA, "public")
        //properties.setProperty(AvailableSettings.FORMAT_SQL, "true")
        //properties.setProperty(AvailableSettings.SHOW_SQL, "true")
        sessionFactory.setAnnotatedPackages("tk.avabin.tdg.entities")
        sessionFactory.hibernateProperties = properties
        return sessionFactory
    }

    @Bean
    fun getTransactionManager(): HibernateTransactionManager {
        val transactionManager = HibernateTransactionManager()
        transactionManager.sessionFactory = getSessionFactory().`object`
        return transactionManager
    }

    @Bean(name = arrayOf("dataSource"))
    fun getDataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        val url = StringBuilder("jdbc:postgresql://")
                    .append(System.getenv("POSTGRES_ADDR"))
                .append(":").append(System.getenv("POSTGRES_PORT"))
                .append("/").append(System.getenv("POSTGRES_DB"))
                .toString()
        dataSource.setDriverClassName("org.postgresql.Driver")
        dataSource.url = url
        dataSource.username = System.getenv("POSTGRES_USER")
        dataSource.password = System.getenv("POSTGRES_PASSWORD")
        return dataSource
    }
}