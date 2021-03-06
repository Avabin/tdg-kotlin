package tk.avabin.tdg

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

/**
 * @author Avabin
 */
@SpringBootApplication
@ComponentScan(basePackages = arrayOf("tk.avabin.tdg"))
class TDGApplication
fun main(args: Array<String>) {
    SpringApplication.run(TDGApplication::class.java, *args)
}