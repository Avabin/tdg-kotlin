package tk.avabin.tdg.init

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import tk.avabin.tdg.daos.PrivilegeRepository
import tk.avabin.tdg.daos.RoleRepository
import tk.avabin.tdg.daos.UserEntityRepository
import tk.avabin.tdg.entities.Privilege
import tk.avabin.tdg.entities.Role
import tk.avabin.tdg.entities.UserEntity
import javax.transaction.Transactional

@Component
open class InitialDataLoader : ApplicationListener<ContextRefreshedEvent> {
    private val logger = LoggerFactory.getLogger(InitialDataLoader::class.java)
    var alreadySetup = false
    @Autowired private lateinit var userEntityRepository: UserEntityRepository
    @Autowired private lateinit var roleRepository: RoleRepository
    @Autowired private lateinit var privilegeRepository: PrivilegeRepository
    @Autowired private lateinit var passwordEncoder: PasswordEncoder

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if(!alreadySetup) {
            val readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE")
            val writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE")
            val adminPrivileges = HashSet<Privilege>(2)
            adminPrivileges.add(readPrivilege)
            adminPrivileges.add(writePrivilege)

            val adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges)
            val userRole = createRoleIfNotFound("ROLE_USER", setOf(readPrivilege))

            val adminUser = UserEntity(
                id=0,
                username = "Admin",
                password = passwordEncoder.encode("testpass"),
                roles = HashSet(setOf(adminRole, userRole))
            )
            logger.info("Admin added with username {}", adminUser.username)
            userEntityRepository.save(adminUser)

            alreadySetup = true

        }
    }

    @Transactional
    fun createPrivilegeIfNotFound(name: String): Privilege {
        var privilege = Privilege(name=name)
        privilege = privilegeRepository.save(privilege)
        return privilege
    }

    @Transactional
    fun createRoleIfNotFound(
        name: String, privileges: Set<Privilege>): Role {
        var role = Role(name=name)
        role.privileges = privileges
        role = roleRepository.save(role)
        return role
    }
}