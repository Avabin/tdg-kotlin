package tk.avabin.tdg.config

import org.springframework.boot.SpringBootConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@SpringBootConfiguration
class SecurityConfig :  WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .anonymous().disable()
            .authorizeRequests()
            .antMatchers("/oauth/token").permitAll()
    }


}