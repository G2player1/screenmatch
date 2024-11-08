package Enos.projetoSpring.screenmatch.repository;

import Enos.projetoSpring.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ISerieRepository extends JpaRepository<Serie,Long> {

    List<Optional<Serie>> findByTitleContainingIgnoreCase(String title);
}
