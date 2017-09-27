package tk.avabin.tdg.controllers

import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tk.avabin.tdg.daos.UserEntityService
import tk.avabin.tdg.dtos.UserDto
import tk.avabin.tdg.entities.UserEntity

/**
 * @author Avabin
 */
@RestController
@RequestMapping("user")
class UserRestController (
        private @Autowired val userEntityService: UserEntityService,
        private @Autowired val passwordEncoder: PasswordEncoder,
        private @Autowired val modelMapper: ModelMapper
) {

    @PostMapping
    fun addUser(@RequestBody userDto: UserDto) : ResponseEntity<UserDto> {
        val user = modelMapper.map(userDto, UserEntity::class.java)
        val existingUser: UserEntity? = userEntityService.findOneByUsername(user.username)
        return if(existingUser == null) {
            user.password = passwordEncoder.encode(user.password)
            val savedUser = userEntityService.saveOrUpdate(user)
            ResponseEntity(
                    modelMapper.map(savedUser, UserDto::class.java),
                    HttpStatus.CREATED
            )
        } else {
            ResponseEntity(modelMapper.map(existingUser, UserDto::class.java), HttpStatus.FOUND)
        }
    }
}