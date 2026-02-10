package com.example.movie_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie_service.Model.Movie;
import com.example.movie_service.exception.InvalidMovieException;
import com.example.movie_service.exception.MovieNotFoundException;
import com.example.movie_service.repo.MovieRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	//CRUD operations: create, update, delete, fetch
	
	public Movie create(Movie movie)
	{
		if(movie==null)
			throw new InvalidMovieException("Invalid Movie: null");
		
		return movieRepository.save(movie);
	}
	
	public Movie read(Long id)
	{
		return movieRepository.findById(id).orElseThrow(()->new MovieNotFoundException("Movie not found: "+id));
	}
	
	public Movie update(Movie movie,Long id)
	{
		if(movie==null || id==null)
			throw new InvalidMovieException("Invalid Movie: null");
			
			
//		if(movieRepository.existsById(id))
//		{
//			Movie updateMovie= movieRepository.getById(id);
//			updateMovie.setName(movie.getName());
//			updateMovie.setDirector(movie.getDirector());
//			updateMovie.setActors(movie.getActors());
//			movieRepository.save(updateMovie);			
//		}
//		else
//		{
//			throw new RuntimeException("Invalid Movie");
//		}
		
		Movie updateMovie = movieRepository.findById(id)
	            .orElseThrow(() -> new MovieNotFoundException("Movie not found: "+id));

	    updateMovie.setName(movie.getName());
	    updateMovie.setDirector(movie.getDirector());
	    updateMovie.setActors(movie.getActors());

	    Movie m1= movieRepository.save(updateMovie);
	    return m1;
	}
	public void delete(Long id)
	{
		if(movieRepository.existsById(id))
		{
			 movieRepository.deleteById(id);
		}
		else
		{
			throw new InvalidMovieException("Invalid Movie: null");
		}
		
	}	

}
