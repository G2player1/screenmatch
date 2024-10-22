package Enos.projetoSpring.screenmatch.models;

public class Episode {
    private String title;
    private Integer episodeNumber;

    public Episode(String title, Integer episodeNumber) {
        this.title = title;
        this.episodeNumber = episodeNumber;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public String getTitle() {
        return title;
    }
}
