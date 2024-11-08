package Enos.projetoSpring.screenmatch.repository;

import Enos.projetoSpring.screenmatch.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IMovieRepository extends JpaRepository<Movie, Long> {

    List<Optional<Movie>> findByTitleContainingIgnoreCase(String title);
}
