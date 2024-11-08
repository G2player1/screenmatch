package Enos.projetoSpring.screenmatch.models.omdbData;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonData(@JsonAlias("Title") String title,
                         @JsonAlias("Season") Integer seasonNumber,
                         @JsonAlias("Episodes") List<EpisodeSimpleData> episodes) {
}
