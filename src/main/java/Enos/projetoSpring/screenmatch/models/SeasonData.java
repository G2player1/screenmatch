package Enos.projetoSpring.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonData(@JsonAlias("Title") String title,
                         @JsonAlias("Season") Integer seasonNumber,
                         @JsonAlias("Episodes") Object[] episodes) {
}
