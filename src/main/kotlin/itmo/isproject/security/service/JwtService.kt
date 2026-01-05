package itmo.isproject.security.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import itmo.isproject.model.user.Role
import itmo.isproject.model.user.User
import itmo.isproject.security.config.SecurityConfigProperties
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

private val logger = KotlinLogging.logger {}

@Service
class JwtService(
    securityConfigProperties: SecurityConfigProperties
) {
    private val secretKey: String = securityConfigProperties.secretKey
    private val jwtExpiration: Long = securityConfigProperties.expiration

    fun generateToken(user: User): String {
        withLoggingContext("userId" to user.id.toString(), "username" to user.usernameInternal) {
            logger.debug { "Generating JWT token" }
        }
        val extraClaims: Map<String, Any> = mapOf(
            "id" to user.id!!,
            "role" to user.role!!.name,
            "enabled" to user.enabled
        )
        return buildToken(extraClaims, user, jwtExpiration)
    }

    private fun buildToken(
        extraClaims: Map<String, Any>,
        userDetails: UserDetails,
        expiration: Long
    ): String {
        val now = Date(System.currentTimeMillis())

        return Jwts.builder()
            .claims(extraClaims)
            .subject(userDetails.username)
            .issuedAt(now)
            .expiration(Date(now.time + expiration))
            .signWith(getSignInKey())
            .compact()
    }

    fun parseUser(token: String): User {
        logger.debug { "Parsing JWT token" }
        val claims = extractAllClaims(token)

        if (claims.expiration.before(Date())) {
            withLoggingContext("username" to claims.subject) {
                logger.warn { "JWT token has expired" }
            }
            throw JwtException("JWT has expired")
        }

        return User().apply {
            id = claims["id"] as Int?
            usernameInternal = claims.subject
            role = Role.valueOf(claims["role"] as String)
            enabled = claims["enabled"] as Boolean
        }
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun getSignInKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}
