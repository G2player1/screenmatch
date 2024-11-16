package Enos.projetoSpring.screenmatch.repository;

import Enos.projetoSpring.screenmatch.models.Serie;
import Enos.projetoSpring.screenmatch.models.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ISerieRepository extends JpaRepository<Serie,Long> {

    List<Optional<Serie>> findByTitleContainingIgnoreCase(String title);

}
