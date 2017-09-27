package tk.avabin.tdg.entities

import lombok.NoArgsConstructor
import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

@NoArgsConstructor
class AppUser(
    var authorities: HashSet<GrantedAuthority> = HashSet(),
    var enabled: Boolean = true,
    var credentialsNonExpired: Boolean = true
) : UserDetails {

    override fun isEnabled(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUsername(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCredentialsNonExpired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPassword(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isAccountNonExpired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isAccountNonLocked(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}