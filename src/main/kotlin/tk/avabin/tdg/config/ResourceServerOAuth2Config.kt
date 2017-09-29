package tk.avabin.tdg.config

import org.springframework.boot.SpringBootConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler

@SpringBootConfiguration
@EnableResourceServer
class ResourceServerOAuth2Config : ResourceServerConfigurerAdapter() {

    @JvmField
    final val RESOURCE_ID = "TDG_REST_API"

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId(RESOURCE_ID).stateless(false)
    }

    override fun configure(http: HttpSecurity) {
        http.anonymous().disable()
            .requestMatchers().antMatchers("/admin/**")
            .and().authorizeRequests()
            .antMatchers("/admin/**").access("hasRole('ADMIN')")
            .and().exceptionHandling().accessDeniedHandler(OAuth2AccessDeniedHandler())
    }

}