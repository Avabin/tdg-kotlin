package tk.avabin.tdg.daos

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import tk.avabin.tdg.entities.Privilege
import javax.transaction.Transactional

@Transactional
interface PrivilegeRepository : JpaRepository<Privilege, Int> {
    fun findOneByName (name: String) : Privilege
}

interface PrivilegeService {
    fun getByName(name: String) : Privilege
    fun saveOrUpdate(s: Privilege) : Privilege
    fun delete(s: Privilege)
}

@Service
class PrivilegeServiceImpl (
    private @Autowired val repository: PrivilegeRepository
) : PrivilegeService {
    override fun getByName(name: String): Privilege {
        return repository.findOneByName(name)
    }

    override fun saveOrUpdate(s: Privilege): Privilege {
        return repository.save(s)
    }

    override fun delete(s: Privilege) {
        repository.delete(s)
    }
}