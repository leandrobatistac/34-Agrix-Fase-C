package com.betrybe.agrix.controllers;


import com.betrybe.agrix.dto.AuthenticationDto;
import com.betrybe.agrix.dto.TokenDto;
import com.betrybe.agrix.model.entities.Person;
import com.betrybe.agrix.service.PersonService;
import com.betrybe.agrix.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Javadoc.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final PersonService personService;
  private final TokenService tokenService;

  /**
   * Javadoc.
   */
  public AuthenticationController(
      AuthenticationManager authenticationManager,
      PersonService personService,
      TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.personService = personService;
    this.tokenService = tokenService;
  }
  /**
   * Javadoc.
   */

  @PostMapping("/login")

  public ResponseEntity<TokenDto> login(@RequestBody AuthenticationDto authenticationDto) {
    UsernamePasswordAuthenticationToken usernamePassword =
        new UsernamePasswordAuthenticationToken(
            authenticationDto.username(), authenticationDto.password());
    Authentication auth = authenticationManager.authenticate(usernamePassword);
    Person person = (Person) auth.getPrincipal();
    String token = tokenService.generateToken(person);
    TokenDto tokenDto = new TokenDto(token);
    return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
  }
}
