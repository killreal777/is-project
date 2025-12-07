package itmo.isproject.security.filter

import io.jsonwebtoken.JwtException
import itmo.isproject.security.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = getJwtFromRequest(request) ?: run {
            filterChain.doFilter(request, response)
            return
        }

        val userDetails: UserDetails
        try {
            userDetails = jwtService.parseUser(jwt)
            if (!userDetails.isEnabled) {
                filterChain.doFilter(request, response)
                return
            }
        } catch (_: JwtException) {
            filterChain.doFilter(request, response)
            return
        }

        val authToken = UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.authorities
        )
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authToken
        
        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7)
        }
        return null
    }
}
