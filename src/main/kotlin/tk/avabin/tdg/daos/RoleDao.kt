package tk.avabin.tdg.daos

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import tk.avabin.tdg.entities.Role
import javax.transaction.Transactional

@Transactional
interface RoleRepository : JpaRepository<Role, Int> {
    fun findOneByName(name: String) : Role
}

interface RoleService{
    fun saveOrUpdate(r: Role) : Role
    fun getByName(name: String) : Role
    fun delete(r: Role)
}

@Service
class RoleServiceImpl(
    private @Autowired val repository: RoleRepository
) : RoleService {
    override fun saveOrUpdate(r: Role): Role {
        return repository.save(r)
    }

    override fun getByName(name: String): Role {
        return repository.findOneByName(name)
    }

    override fun delete(r: Role) {
        repository.delete(r)
    }
}