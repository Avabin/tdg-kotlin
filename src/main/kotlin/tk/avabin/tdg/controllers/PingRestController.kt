package tk.avabin.tdg.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Avabin
 */
@RestController
class PingRestController {

    @GetMapping("/health")
    fun health() : String {
        return "ok"
    }
}