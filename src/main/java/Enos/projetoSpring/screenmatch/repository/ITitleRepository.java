package Enos.projetoSpring.screenmatch.repository;

import Enos.projetoSpring.screenmatch.models.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ITitleRepository extends JpaRepository<Title,Long> {

    List<Optional<Title>> findByTitleContainingIgnoreCase(String title);

    @Query(value = "SELECT * FROM titles WHERE titles.title ILIKE %:titleSearch%",nativeQuery = true)
    List<Optional<Title>> searchTitlesByTitle(@Param("titleSearch") String titleSearch);
}
