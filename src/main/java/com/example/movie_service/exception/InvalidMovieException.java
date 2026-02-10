package com.example.movie_service.exception;

public class InvalidMovieException extends RuntimeException{
	
	public InvalidMovieException(String movie)
	{
		super(movie);
	}

}
