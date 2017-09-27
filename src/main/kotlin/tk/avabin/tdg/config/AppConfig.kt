package tk.avabin.tdg.config

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.config.annotation.EnableWebMvc

/**
 * @author Avabin
 */
@Configuration
@EnableWebMvc
class AppConfig {

    @Bean
    fun modelMapper() : ModelMapper {
        return ModelMapper()
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}