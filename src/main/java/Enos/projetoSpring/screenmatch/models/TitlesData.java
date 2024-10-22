package Enos.projetoSpring.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TitlesData(@JsonAlias("Title") String title,@JsonAlias("Year") String year,
                         @JsonAlias("Released") String released,@JsonAlias("Runtime") String runtime,
                         @JsonAlias("Genre") String genre,@JsonAlias("Director") String director,
                         @JsonAlias("Writer") String writer,@JsonAlias("Actors") String actors,
                         @JsonAlias("Plot") String plot,@JsonAlias("Language") String language,
                         @JsonAlias("Awards") String awards,@JsonAlias("Poster") String poster,
                         @JsonAlias("imdbRating") String imdbRating,@JsonAlias("imdbVotes") String imdbVotes,
                         @JsonAlias("Type") String type,@JsonAlias("TotalSeasons") Integer totalSeasons) {
}
