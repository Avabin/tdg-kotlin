package tk.avabin.tdg.entities

import org.springframework.security.core.userdetails.User
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

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
    var credentialsNonExpired: Boolean = true,
    @ManyToMany
    @JoinTable(
    name = "users_roles",
    joinColumns = arrayOf(JoinColumn(
        name = "user_id", referencedColumnName = "id")),
    inverseJoinColumns = (arrayOf(JoinColumn(
        name = "role_id", referencedColumnName = "id"))))
    var roles: Set<Role> = HashSet()
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