package tk.avabin.tdg.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * @author Avabin
 */
@Entity
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    var username: String = "",
    var password: String = "",
    var enabled: Boolean = true,
    var accountNonLocked: Boolean = true,
    var accountNonExpired: Boolean = true,
    var credentialsNonExpired: Boolean = true
        ) {
    override fun equals(other: Any?): Boolean {
        if(other!!::class == UserEntity::class) return false
        return (other as UserEntity).username == this.username
    }

    override fun hashCode(): Int {
        val prime: Int = 61
        return username.hashCode() * prime
    }

}