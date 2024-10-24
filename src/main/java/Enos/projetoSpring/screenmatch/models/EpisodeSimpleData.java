package Enos.projetoSpring.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeSimpleData(@JsonAlias("Title") String title,
                                @JsonAlias("Episode") Integer episodeNumber) {
}
