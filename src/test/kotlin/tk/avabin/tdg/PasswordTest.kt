package tk.avabin.tdg

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import tk.avabin.tdg.config.AppConfig

@SpringBootTest(classes = arrayOf(AppConfig::class))
@RunWith(SpringJUnit4ClassRunner::class)
class PasswordTest {

    private @Autowired lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun testPasswordEncoder() {
        val raw = "passwiord123123123"
        val encoded = passwordEncoder.encode(raw)
        assert(passwordEncoder.matches(raw, encoded))
    }
}