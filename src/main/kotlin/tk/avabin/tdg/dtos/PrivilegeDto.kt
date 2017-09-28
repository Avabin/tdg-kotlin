package tk.avabin.tdg.dtos

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.NoArgsConstructor
import java.io.Serializable

@NoArgsConstructor
data class PrivilegeDto(
    var id: Int = 0,
    var name: String = "",
    @JsonIgnore
    var roles: Set<RoleDto> = HashSet()
) : Serializable {
    override fun equals(other: Any?): Boolean {
        return if (other == null || other.javaClass != this.javaClass) false
        else (other as PrivilegeDto).name == this.name
    }

    override fun hashCode(): Int {
        val prime = 13
        return name.hashCode() * prime
    }
}