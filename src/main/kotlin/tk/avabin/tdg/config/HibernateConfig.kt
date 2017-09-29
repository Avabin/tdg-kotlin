package tk.avabin.tdg.config

import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.datasource.init.DataSourceInitializer
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.Properties
import javax.sql.DataSource

/**
 * @author Avabin
 */
@SpringBootConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = arrayOf("tk.avabin.tdg.daos"))
class HibernateConfig {

    @Autowired
    @Bean(name = arrayOf("sessionFactory"))
    fun getSessionFactory(dataSource: DataSource): LocalSessionFactoryBean {
        val sessionFactory = LocalSessionFactoryBean()
        sessionFactory.setDataSource(dataSource)
        val properties = Properties()
        properties.setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect")
        properties.setProperty(AvailableSettings.DEFAULT_SCHEMA, "public")
        properties.setProperty(AvailableSettings.FORMAT_SQL, "true")
        properties.setProperty(AvailableSettings.SHOW_SQL, "true")
        sessionFactory.setAnnotatedPackages("tk.avabin.tdg.entities")
        sessionFactory.hibernateProperties = properties
        return sessionFactory
    }

    @Bean
    @Autowired
    fun dataSourceInitializer(dataSource: DataSource): DataSourceInitializer {
        val rdp = ResourceDatabasePopulator()
        rdp.addScript(ClassPathResource("/schema-postgres.sql"))
        val dsi = DataSourceInitializer()
        dsi.setDataSource(dataSource)
        dsi.setDatabasePopulator(rdp)
        return dsi
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