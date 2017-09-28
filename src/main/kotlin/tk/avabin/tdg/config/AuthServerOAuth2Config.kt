package tk.avabin.tdg.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import javax.sql.DataSource

@Configuration
@EnableAuthorizationServer
class AuthServerOAuth2Config : AuthorizationServerConfigurerAdapter() {

    @Autowired private lateinit var authenticationManager: AuthenticationManager
    @Autowired private lateinit var dataSource: DataSource

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()")
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource)
            .withClient("TestAdmin")
            .secret("TestSecret")
            .authorizedGrantTypes("password", "authorization_code", "refresh_code")
            .scopes("read", "write")
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
            .tokenStore(tokenStore(dataSource))
            .authenticationManager(authenticationManager)
    }

    @Bean
    @Autowired
    fun tokenStore(dataSource: DataSource) : TokenStore {
        return JdbcTokenStore(dataSource)
    }
}