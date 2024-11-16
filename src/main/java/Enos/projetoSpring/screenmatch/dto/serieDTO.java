package Enos.projetoSpring.screenmatch.dto;

import java.util.List;

public record serieDTO(String poster, String title, Integer runtime, String sinopse, String released, String awards,
                       String language, Integer year, Double rating, Integer totalVotes, List<EmployeeDTO> employeeList,
                       List<GenreDTO> genreList) {
}
