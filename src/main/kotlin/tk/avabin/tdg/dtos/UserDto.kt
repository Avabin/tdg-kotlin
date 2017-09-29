package tk.avabin.tdg.dtos

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

/**
 * @author Avabin
 */
data class UserDto(
    var id: Int = 0,
    var username: String = "",
    private var _password: String = "",
    var enabled: Boolean = true,
    var accountNonLocked: Boolean = true,
    var accountNonExpired: Boolean = true,
    var credentialsNonExpired: Boolean = true,
    var roles: Set<RoleDto> = HashSet()
) : Serializable {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var password = _password
        @JsonIgnore
        get() = _password
        @JsonProperty
        set(value) {
            _password = value
            field = value
        }

    override fun equals(other: Any?): Boolean {
        if(other!!::class == this::class) return false
        return (other as UserDto).username == this.username
    }

    override fun hashCode(): Int {
        val prime: Int = 61
        return username.hashCode() * prime
    }
}