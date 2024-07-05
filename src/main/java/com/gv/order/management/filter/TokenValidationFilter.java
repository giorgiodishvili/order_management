package com.gv.order.management.filter;

import com.gv.order.management.client.user.api.AuthenticationControllerApiClient;
import com.gv.order.management.client.user.model.TokenValidationResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TokenValidationFilter extends OncePerRequestFilter {

    private final AuthenticationControllerApiClient authenticationControllerApiClient;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!validateAndSetAuthentication(token)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid or missing token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @SneakyThrows
    private boolean validateAndSetAuthentication(final String token) {
        final TokenValidationResponse response =
                authenticationControllerApiClient.validateToken(token).getBody();

        if (response == null) {
            return false;
        }
        if (response.getValid()) {
            final List<SimpleGrantedAuthority> authorities = response.getPermissions().stream()
                    .map(it -> new SimpleGrantedAuthority(it.getAuthority()))
                    .collect(Collectors.toList());
            final User user = new LoggedInUser(response.getUsername(), "", authorities, response.getId());
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(user, token, authorities));
            return true;
        }
        return false;
    }
}
