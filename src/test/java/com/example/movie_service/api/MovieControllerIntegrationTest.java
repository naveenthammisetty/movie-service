package com.example.movie_service.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.movie_service.Model.Movie;
import com.example.movie_service.repo.MovieRepository;

import tools.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    public void setup() {
        movieRepository.deleteAll(); // clear H2 DB before each test
    }

    @Test
    void givenMovie_whenCreateMovie_thenReturnSavedMovie() throws Exception {
        // given
        Movie movie = new Movie();
        movie.setName("RRR");
        movie.setDirector("Rajamouli");
        movie.setActors(List.of("NTR, Ram Charan, Alia Bhat"));

        // when & then
        mockMvc.perform(post("/movies/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("RRR"))
                .andExpect(jsonPath("$.director").value("Rajamouli"))
                .andExpect(jsonPath("$.actors").value("NTR, Ram Charan, Alia Bhat"));
    }
    
    @Test
    void shouldGetMovieById() throws Exception
    {
    	// given
        Movie movie = new Movie();
        movie.setName("RRR");
        movie.setDirector("Rajamouli");
        movie.setActors(List.of("NTR, Ram Charan, Alia Bhat"));
        
        Movie getMovie=movieRepository.save(movie);
         
        mockMvc.perform(get("/movies/{id}",getMovie.getId()))
        		.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("RRR"))
                .andExpect(jsonPath("$.director").value("Rajamouli"))
                .andExpect(jsonPath("$.actors").value("NTR, Ram Charan, Alia Bhat"));
    	
    }
    
    @Test
    void shouldDeleteMovie() throws Exception
    {
    	Movie movie = new Movie();
        movie.setName("RRR");
        movie.setDirector("Rajamouli");
        movie.setActors(List.of("NTR, Ram Charan, Alia Bhat"));
        
        Movie getMovie=movieRepository.save(movie);
        mockMvc.perform(delete("/movies/{id}",getMovie.getId()))
		.andExpect(status().isOk());
    	
    }
    
    @Test
    void shouldUpdateMovie() throws Exception {
        // given: create and save a movie
        Movie movie = new Movie();
        movie.setName("RRR");
        movie.setDirector("Rajamouli");
        movie.setActors(List.of("NTR", "Ram Charan", "Alia Bhat"));

        Movie savedMovie = movieRepository.save(movie);

        // update data
        Movie updatedMovie = new Movie();
        updatedMovie.setName("RRR Updated");
        updatedMovie.setDirector("Rajamouli Updated");
        updatedMovie.setActors(List.of("NTR", "Ram Charan"));

        // when & then
        mockMvc.perform(put("/movies/{id}", savedMovie.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedMovie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("RRR Updated"))
                .andExpect(jsonPath("$.director").value("Rajamouli Updated"))
                .andExpect(jsonPath("$.actors[0]").value("NTR"))
                .andExpect(jsonPath("$.actors[1]").value("Ram Charan"));
    }


    
    
}
