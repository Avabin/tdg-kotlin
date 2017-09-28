package tk.avabin.tdg.entities

import org.codehaus.jackson.annotate.JsonIgnore
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
data class Privilege(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    var name: String = "",
    @JsonIgnore
    @ManyToMany(mappedBy = "privileges")
    var roles: Set<Role> = HashSet()
) : Serializable {
    override fun equals(other: Any?): Boolean {
        return if (other == null || other.javaClass != Privilege::class.java) false
        else (other as Privilege).name == this.name
    }

    override fun hashCode(): Int {
        val prime = 13
        return name.hashCode() * prime
    }
}