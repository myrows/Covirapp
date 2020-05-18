package com.salesianostriana.dam.covirapp.entities

import com.salesianostriana.dam.covirapp.annotation.NoArg
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.util.*
import javax.persistence.*
import kotlin.collections.HashSet

@Entity
@NoArg
@Table(name = "\"user\"")
data class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private val id: Long = 0,

        @Column(nullable = false, unique = true)
        private var username: String,

        @Column(nullable = false)
        private var password: String,

        @Column(name = "full_name")
        var fullName : String,

        @Column(name = "province")
        var province : String,

        @Column(name = "status")
        @Enumerated(EnumType.STRING)
        var status : Status,

        @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL])
        var quiz : Quiz? = null,

        @ManyToMany
        @JoinTable(
                name = "user_role",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")]
        )
        val roles: Set<Role> = HashSet(),

        @Transient
        private var authorities: MutableCollection<out GrantedAuthority> = HashSet(),

        @Column(name = "non_expired", nullable = false)
        private val nonExpired: Boolean = true,
        @Column(name = "non_locked", nullable = false)
        private val nonLocked: Boolean = true,
        @Column(nullable = false)
        private val enabled: Boolean = true,
        @Column(name = "credentials_non_expired", nullable = false)
        private val credentialsNonExpired: Boolean = true
) : UserDetails, Serializable {
    override fun getUsername() = username
    override fun getPassword() = password
    override fun isAccountNonExpired() = nonExpired
    override fun isAccountNonLocked() = nonLocked
    override fun isEnabled() = enabled
    override fun isCredentialsNonExpired() = credentialsNonExpired
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

    override fun hashCode(): Int {
        if (id == 0L) {
            return super.hashCode()
        }
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other === null || other !is User) {
            return false
        }
        if (this::class != other::class) {
            return false
        }
        return id == other.id
    }
}