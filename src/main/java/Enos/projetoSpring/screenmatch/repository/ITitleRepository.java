package Enos.projetoSpring.screenmatch.repository;

import Enos.projetoSpring.screenmatch.models.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITitleRepository extends JpaRepository<Title,Long> {

    List<Optional<Title>> findByTitleContainingIgnoreCase(String title);

    List<Title> findByTypeContainingIgnoreCase(String type);

    List<Title> findTop5ByTypeOrderByRating(String type);
}
