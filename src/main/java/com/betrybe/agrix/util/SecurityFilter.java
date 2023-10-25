package com.betrybe.agrix.util;

import com.betrybe.agrix.service.PersonService;
import com.betrybe.agrix.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {
  private final TokenService tokenService;
  private final PersonService personService;

  @Autowired
  public SecurityFilter(TokenService tokenService, PersonService personService) {
    this.tokenService = tokenService;
    this.personService = personService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {
    String token = token(request);

    if (token != null) {
      String test = tokenService.validateToken(token);
      UserDetails userDetails = personService.loadUserByUsername(test);

      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
      );

      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    filterChain.doFilter(request, response);

  }

  private String token(HttpServletRequest request) {
    String auth = request.getHeader("Authorization");

    if (auth == null) {
      return null;
    }
    return auth.replace("Bearer ", "");
  }
}