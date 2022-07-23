package io.agileinteligence.ppmtool.security;

import static io.agileinteligence.ppmtool.security.SecurityConstants.*;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.agileinteligence.ppmtool.domain.User;
import io.agileinteligence.ppmtool.services.CustomUserDetailsService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {

	try {
	    String jwt = getJwtFromRequest(request);
	    if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
		Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);
		User userDetails = customUserDetailsService.loadByUserById(userId);
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
			Collections.emptyList());
		auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(auth);
	    }
	} catch (Exception e) {
	    logger.error("セキュリティコンテキストでユーザー認証を設定できませんでした", e);
	}
	filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {

	String bearerToken = request.getHeader(HEADER_STRING);
	if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
	    return bearerToken.substring(7, bearerToken.length());
	}
	return null;
    }

}
