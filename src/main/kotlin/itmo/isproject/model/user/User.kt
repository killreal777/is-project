package itmo.isproject.model.user

import itmo.isproject.model.IntIdEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User(

    @NotBlank
    @Size(max = 32)
    @Column(name = "username", nullable = false, unique = true, length = 32)
    var usernameInternal: String? = null,

    @NotBlank
    @Column(name = "password", nullable = false)
    var passwordInternal: String? = null,

    @NotNull
    @Column(name = "role", length = 16, nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role? = null,

    @NotNull
    @Column(name = "enabled", nullable = false)
    var enabled: Boolean = false

) : IntIdEntity(), UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return setOf(SimpleGrantedAuthority(role?.name))
    }

    override fun getPassword() = passwordInternal

    override fun getUsername() = usernameInternal

    override fun isEnabled() = enabled

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true
}
