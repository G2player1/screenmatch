package Enos.projetoSpring.screenmatch.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private String title;
    private Integer seasonNumber;
    private Integer episodeNumber;
    private LocalDate released;
    private Double rating;
    private Integer runtime;
    private String plot;

    public Episode(String title, Integer episodeNumber) {
        this.title = title;
        this.episodeNumber = episodeNumber;
    }

    public Episode(EpisodeDetailedData episodeDetailedData) {
        this.title = episodeDetailedData.title();
        this.seasonNumber = episodeDetailedData.seasonNumber();
        this.episodeNumber = episodeDetailedData.episodeNumber();
        this.runtime = Integer.parseInt(episodeDetailedData.runtime().replaceAll("([^0-9]+)",""));
        this.plot = episodeDetailedData.episodePlot();
        try {
            this.released = LocalDate.parse(episodeDetailedData.released());
            this.rating = Double.parseDouble(episodeDetailedData.rating());
        } catch (DateTimeParseException | NumberFormatException e) {
            this.released = null;
        }
    }

    public Episode(Integer seasonNumber,EpisodeSimpleData episodeSimpleData){
        this.title = episodeSimpleData.title();
        this.episodeNumber = episodeSimpleData.episodeNumber();
        this.seasonNumber = seasonNumber;
        try {
            this.released = LocalDate.parse(episodeSimpleData.released());
            this.rating = Double.parseDouble(episodeSimpleData.rating());
        } catch (DateTimeParseException | NumberFormatException e) {
            this.released = null;
        }
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public Double getRating() {
        return rating;
    }

    public LocalDate getReleased() {
        return released;
    }

    public String getPlot() {
        return plot;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Epsiode Title: " + title + '\n' +
                "Episode Number: " + episodeNumber + "\n" +
                "Release Data: " + released + "\n" +
                "Rating: " + rating + "\n" +
                "Runtime: " + runtime + "\n" +
                "Plot: " + plot + "\n\n";
    }
}
