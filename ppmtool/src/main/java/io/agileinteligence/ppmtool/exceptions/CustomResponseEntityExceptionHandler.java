package io.agileinteligence.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException(ProjectIdException ex, WebRequest request) {

	ProjectIdExceptionResponse projectIdExceptionResponse = new ProjectIdExceptionResponse(ex.getMessage());
	return new ResponseEntity(projectIdExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex,
	    WebRequest request) {

	ProjectNotFoundExceptionResponse projectNotFoundExceptionResponse = new ProjectNotFoundExceptionResponse(
		ex.getMessage());
	return new ResponseEntity(projectNotFoundExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex,
	    WebRequest request) {

	UsernameAlreadyExistsResponse usernameAlreadyExistsResponse = new UsernameAlreadyExistsResponse(
		ex.getMessage());
	return new ResponseEntity(usernameAlreadyExistsResponse, HttpStatus.BAD_REQUEST);
    }

}
