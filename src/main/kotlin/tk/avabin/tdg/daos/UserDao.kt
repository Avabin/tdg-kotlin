package tk.avabin.tdg.daos

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tk.avabin.tdg.entities.UserEntity

/**
 * @author Avabin
 */
interface UserEntityRepository : JpaRepository<UserEntity, Int> {
    fun findOneByUsername(username: String) : UserEntity
}

interface UserEntityService {
    fun findOneByUsername(username: String): UserEntity
    fun saveOrUpdate(u: UserEntity): UserEntity
    fun delete(u: UserEntity)
    fun delete(userId: Int)
}

@Service
@Transactional
class UserEntityServiceImpl(
        private @Autowired val repository: UserEntityRepository
) : UserEntityService {

    override fun findOneByUsername(username: String): UserEntity {
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