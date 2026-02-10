package com.example.movie_service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movie_service.Model.Movie;
import com.example.movie_service.service.MovieService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/movies")
@Slf4j
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Movie> getMovie(@PathVariable Long id)
	{
		Movie movie=movieService.read(id);
		log.info("Get movie");
		return ResponseEntity.ok(movie);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Movie> updateMovie(@RequestBody(required = false) Movie movie, @PathVariable Long id)
	{
		 log.info("Update movie");
		 Movie updateMovies=movieService.update(movie, id);
		 return ResponseEntity.ok(updateMovies);
		
	}
	
	@PostMapping("/save")
	public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie)
	{
		Movie movies=movieService.create(movie);
		log.info("save movie");
		return ResponseEntity.ok(movie);
		
	}
	
	@DeleteMapping("/{id}")
	public void deleteMovie(@PathVariable Long id)
	{
		log.info("delete movie");
		movieService.delete(id);
	}

}
