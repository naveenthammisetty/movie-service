package com.example.movie_service.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.movie_service.exception.InvalidMovieException;
import com.example.movie_service.exception.MovieNotFoundException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@Getter
	static class Error
	{
		private final String message;
		
		private final String reason;
		
		public Error(String message, String reason)
		{
			this.message=message;
			this.reason=reason;
			
		}
		
	}
	
	//400 Bad Request(suppose i am passing Movie object as a NULL means this http status will show)
	@ExceptionHandler({InvalidMovieException.class, HttpMessageNotReadableException.class})
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Error handleInvalidDataException(Throwable ex) 
	{
		
		log.warn(ex.getMessage());
		return new Error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
		
		
	}
	
	//404 Data Not Found(Based on passed id and against that id data not available in db means this http status will show) 
	@ExceptionHandler(MovieNotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Error handleMovieNotFoundException(MovieNotFoundException ex) 
	{
		
		log.warn(ex.getMessage());
		return new Error(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
				
	}
	
	//500 Internal Server Error(While data fetching time or passing time any db connection failure or other exception handling purpose will use)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Error handleUnknownException(Exception ex) 
	{
		
		log.warn(ex.getMessage());
		return new Error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());
				
	}
	
	
	
	

}
