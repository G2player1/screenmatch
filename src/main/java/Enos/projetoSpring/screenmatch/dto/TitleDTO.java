package Enos.projetoSpring.screenmatch.dto;

import java.time.LocalDate;
import java.util.List;

public record TitleDTO(Long id, String poster, String title, Integer runtime, String sinopse, LocalDate released, String awards,
                       String language, Integer year, Double rating, Integer totalVotes, List<EmployeeDTO> employeeList,
                       List<GenreDTO> genreList) {
}
