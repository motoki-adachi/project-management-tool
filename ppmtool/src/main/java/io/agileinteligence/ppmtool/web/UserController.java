package io.agileinteligence.ppmtool.web;

import static io.agileinteligence.ppmtool.security.SecurityConstants.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.agileinteligence.ppmtool.domain.User;
import io.agileinteligence.ppmtool.payload.JWTLoginSuccessResponse;
import io.agileinteligence.ppmtool.payload.LoginRequest;
import io.agileinteligence.ppmtool.security.JwtTokenProvider;
import io.agileinteligence.ppmtool.services.MapValidationErrorService;
import io.agileinteligence.ppmtool.services.UserService;
import io.agileinteligence.ppmtool.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {

	ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);
	if (errorMap != null) {
	    return errorMap;
	}

	Authentication auth = authenticationManager.authenticate(
		new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

	SecurityContextHolder.getContext().setAuthentication(auth);
	String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(auth);

	return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {

	userValidator.validate(user, bindingResult);

	ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);
	if (errorMap != null) {
	    return errorMap;
	}

	User newUser = userService.saveUser(user);

	return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

}
