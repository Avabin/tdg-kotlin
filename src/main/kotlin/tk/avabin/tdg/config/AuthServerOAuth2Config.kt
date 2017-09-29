package tk.avabin.tdg.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.approval.ApprovalStore
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import javax.sql.DataSource

@SpringBootConfiguration
@EnableAuthorizationServer
class AuthServerOAuth2Config : AuthorizationServerConfigurerAdapter() {

    @JvmField
    final val REALM = "TDG_REALM"

    @Autowired private lateinit var authenticationManager: AuthenticationManager
    @Autowired private lateinit var dataSource: DataSource
    @Autowired private lateinit var passwordEncoder: PasswordEncoder
    @Autowired private lateinit var userApprovalHandler: UserApprovalHandler

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security
            .realm(REALM + "/client")
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder)
            .withClient("TestClientId")
            .secret("testsecret")
            .scopes("read", "write")
            .authorizedGrantTypes("password", "client_credentials", "refresh_token", "access_token")
            .refreshTokenValiditySeconds(600)
            .accessTokenValiditySeconds(120)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
            .tokenStore(tokenStore(dataSource))
            .userApprovalHandler(userApprovalHandler)
            .authenticationManager(authenticationManager)
    }

    @Bean
    @Autowired
    fun tokenStore(dataSource: DataSource) : TokenStore {
        return JdbcTokenStore(dataSource)
    }

    @Bean("userApprovalHandler")
    @Autowired
    fun userApprovalHandler(tokenStore: TokenStore, clientDetailsService: ClientDetailsService): TokenStoreUserApprovalHandler {
        val handler = TokenStoreUserApprovalHandler()
        handler.setTokenStore(tokenStore)
        handler.setClientDetailsService(clientDetailsService)
        handler.setRequestFactory(DefaultOAuth2RequestFactory(clientDetailsService))
        return handler
    }

    @Bean
    @Autowired
    fun approvalStore(tokenStore: TokenStore): ApprovalStore {
        val store = TokenApprovalStore()
        store.setTokenStore(tokenStore)
        return store
    }
}