package Enos.projetoSpring.screenmatch.repository;

import Enos.projetoSpring.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISerieRepository extends JpaRepository<Serie,Long> {

    List<Optional<Serie>> findByTitleContainingIgnoreCase(String title);

    List<Serie> findTop5ByOrderByRating();

    List<Serie> findTop5ByOrderByReleased();

    Serie findSerieById(Long id);


}
