package com.yao.neo4j.repository;


import com.yao.neo4j.entity.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Component;

@Component
public interface MovieRepository extends Neo4jRepository<Movie, Long> {
    Movie findMovieByTitle(String title);
}
