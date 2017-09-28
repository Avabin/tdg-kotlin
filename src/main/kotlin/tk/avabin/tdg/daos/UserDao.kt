package tk.avabin.tdg.daos

import io.reactivex.rxkotlin.toObservable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import tk.avabin.tdg.entities.Privilege
import tk.avabin.tdg.entities.Role
import tk.avabin.tdg.entities.UserEntity
import javax.transaction.Transactional

/**
 * @author Avabin
 */
@Transactional
interface UserEntityRepository : JpaRepository<UserEntity, Int> {
    fun findOneByUsername(username: String) : UserEntity?
}

interface UserEntityService {
    fun getByName(username: String): UserEntity?
    fun saveOrUpdate(u: UserEntity): UserEntity
    fun delete(u: UserEntity)
    fun delete(userId: Int)
}

@Service
class UserEntityServiceImpl(
        private @Autowired val repository: UserEntityRepository
) : UserEntityService {

    override fun getByName(username: String): UserEntity? {
        return repository.findOneByUsername(username)
    }

    override fun saveOrUpdate(u: UserEntity): UserEntity {
        return repository.save(u)
    }

    override fun delete(u: UserEntity) {
        repository.delete(u)
    }

    override fun delete(userId: Int) {
        repository.delete(userId)
    }
}

@Service("userEntityService")
@Transactional
class UserEntityDetailsService(
    @Autowired private val userEntityService: UserEntityService,
    @Autowired private val roleService: RoleService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity: UserEntity = userEntityService.getByName(username) ?: return User("", "",true, true, true, true, getAuthorities(setOf(roleService.getByName("ROLE_USER"))))
        return User(
            userEntity.username,
            userEntity.password,
            userEntity.enabled,
            userEntity.accountNonExpired,
            userEntity.credentialsNonExpired,
            userEntity.accountNonLocked,
            getAuthorities(userEntity.roles)
        )
    }

    fun getAuthorities(roles: Set<Role>): Set<GrantedAuthority> {
        return getGrantedAuthorities(getPrivileges(roles))
    }

    private fun getPrivileges(roles: Set<Role>): Set<String> {
        val privileges = HashSet<String>()
        roles.toObservable()
            .flatMap { r: Role -> r.privileges.toObservable() }
            .map { p: Privilege -> p.name }
            .subscribe( { s: String -> privileges.add(s) } )
        return privileges
    }

    fun getGrantedAuthorities(privileges: Set<String>): Set<GrantedAuthority> {
        val authorities = HashSet<GrantedAuthority>()
        privileges.toObservable().subscribe({ s: String -> authorities.add(SimpleGrantedAuthority(s))})
        return authorities
    }

}