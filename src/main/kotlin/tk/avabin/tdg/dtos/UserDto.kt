package tk.avabin.tdg.dtos

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

/**
 * @author Avabin
 */
data class UserDto(
        var username: String = "",
        private var _password: String = "",
        var enabled: Boolean = true,
        var accountNonLocked: Boolean = true,
        var accountNonExpired: Boolean = true,
        var credentialsNonExpired: Boolean = true
) : Serializable {
    var password = _password
        @JsonIgnore
    get() = _password
        @JsonProperty
    set(value) {
        _password = value
        field = value
    }
}