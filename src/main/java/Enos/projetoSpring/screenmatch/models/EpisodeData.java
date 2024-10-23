package Enos.projetoSpring.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeData(@JsonAlias("Title") String title, @JsonAlias("Season") Integer seasonNumber,
                          @JsonAlias("Episode") Integer episodeNumber, @JsonAlias("Runtime") String runtime,
                          @JsonAlias("Plot") String episodePlot){
}
