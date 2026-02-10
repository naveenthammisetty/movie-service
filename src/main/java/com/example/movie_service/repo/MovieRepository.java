package com.example.movie_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movie_service.Model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
