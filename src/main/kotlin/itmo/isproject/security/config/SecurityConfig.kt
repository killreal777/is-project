package itmo.isproject.security.config

import itmo.isproject.shared.user.repository.UserRepository
import itmo.isproject.security.filter.JwtAuthenticationFilter
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
@EnableMethodSecurity
@EnableConfigurationProperties(SecurityConfigProperties::class)
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val userRepository: UserRepository
) {

    companion object {
        private val WHITE_LIST_URL = arrayOf(
            "/swagger-ui*/**", "/swagger-ui.html",
            "/v3/api-docs/**", "/v3/api-docs.yaml",
            "/api/v*/auth/**"
        )
    }

    @Bean
    fun filterChain(
        http: HttpSecurity,
        authenticationProvider: AuthenticationProvider
    ): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .cors { cors -> cors.configurationSource(corsConfigurationSource()) }
            .authorizeHttpRequests { request ->
                request
                    .requestMatchers(*WHITE_LIST_URL).permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            userRepository.findByUsernameInternal(username)
                ?: throw UsernameNotFoundException("User not found: $username")
        }
    }

    @Bean
    fun authenticationProvider(
        passwordEncoder: PasswordEncoder,
        userDetailsService: UserDetailsService
    ): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder)
        return authProvider
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    private fun corsConfigurationSource(): CorsConfigurationSource {
        return org.springframework.web.cors.UrlBasedCorsConfigurationSource().apply {
            val corsConfiguration = CorsConfiguration().apply {
                allowedOriginPatterns = listOf("*")
                allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                allowedHeaders = listOf("*")
                allowCredentials = true
            }
            registerCorsConfiguration("/**", corsConfiguration)
        }
    }
}
