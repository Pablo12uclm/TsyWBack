package edu.uclm.esi.juegos.http;

import edu.uclm.esi.juegos.dto.ApiResponse;
import edu.uclm.esi.juegos.exhandling.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), null, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAllUncaughtException(Exception ex) {
        // Ideally, log this exception as well
        ApiResponse apiResponse = new ApiResponse("An unexpected error occurred.", null, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // You can add more exception handlers here for other custom exceptions
}
