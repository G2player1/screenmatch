package Enos.projetoSpring.screenmatch.dto;

import java.time.LocalDate;

public record SerieDTO(Long id, String poster, String titulo, Integer runtime, String sinopse, LocalDate released, String awards,
                       String language, Integer year, Double avaliacao, Integer totalVotes, String atores,
                       String categorias) {
}
