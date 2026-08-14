package com.example.demo

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationError(exception: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {
        val errors = exception.bindingResult.allErrors.map {
            it.defaultMessage ?: "Unknown error"
        }
        return ResponseEntity.status(400).body(mapOf("errors" to errors))
    }
}
