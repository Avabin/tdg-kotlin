package tk.avabin.tdg.config

import org.hibernate.SessionFactory
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder
import javax.sql.DataSource


/**
 * @author Avabin
 */
@Configuration
class HibernateConfig {

    @Autowired
    @Bean(name = arrayOf("sessionFactory"))
    fun getSessionFactory(@Qualifier("dataSource") dataSource: DataSource): SessionFactory {
        val sessionBuilder = LocalSessionFactoryBuilder(dataSource)
        sessionBuilder.scanPackages("tk.avabin.tdg.beans.Entities")
        sessionBuilder.setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect")
        sessionBuilder.setProperty(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, "false")
        sessionBuilder.setProperty(AvailableSettings.HBM2DDL_AUTO, "create")
        // sessionBuilder.setProperty(AvailableSettings.SHOW_SQL, "true");
        // sessionBuilder.setProperty(AvailableSettings.FORMAT_SQL, "true");
        return sessionBuilder.buildSessionFactory()
    }

    @Bean(name = arrayOf("dataSource"))
    fun getDataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.postgresql.Driver")
        dataSource.url = System.getenv("DATABASE_URL")
        dataSource.username = System.getenv("DATABASE_USERNAME")
        dataSource.password = System.getenv("DATABASE_PASSWORD")
        return dataSource
    }
}