package Enos.projetoSpring.screenmatch.models.omdbData;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeDetailedData(@JsonAlias("Title") String title, @JsonAlias("Season") Integer seasonNumber,
                                  @JsonAlias("Episode") Integer episodeNumber, @JsonAlias("Runtime") String runtime,
                                  @JsonAlias("Plot") String episodePlot,@JsonAlias("imdbRating") String rating,
                                  @JsonAlias("Released") String released){
}
