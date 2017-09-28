package tk.avabin.tdg.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity
data class Role (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    var name: String = "",
    @ManyToMany(mappedBy = "roles")
    var users: Set<UserEntity> = HashSet(),
    @ManyToMany
    @JoinTable(
        name = "roles_privileges",
        joinColumns = (arrayOf(JoinColumn(
            name = "role_id", referencedColumnName = "id"))),
        inverseJoinColumns = (arrayOf(JoinColumn(
            name = "privilege_id", referencedColumnName = "id"))))
    var privileges: Set<Privilege> = HashSet()
) {
    override fun equals(other: Any?): Boolean {
        return if (other == null || other.javaClass != this.javaClass) false
        else (other as Role).name == this.name
    }

    override fun hashCode(): Int {
        val prime = 59
        return name.hashCode() * prime
    }
}