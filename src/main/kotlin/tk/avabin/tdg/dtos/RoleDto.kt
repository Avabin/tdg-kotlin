package tk.avabin.tdg.dtos

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.NoArgsConstructor
import java.io.Serializable

@NoArgsConstructor
data class RoleDto(
    var id: Int = 0,
    var name: String = "",
    @JsonIgnore
    var users: Set<UserDto> = HashSet(),
    var privileges: Set<PrivilegeDto> = HashSet()
) : Serializable {
    override fun equals(other: Any?): Boolean {
        return if (other == null || other.javaClass != this.javaClass) false
        else (other as RoleDto).name == this.name
    }

    override fun hashCode(): Int {
        val prime = 59
        return name.hashCode() * prime
    }
}