package Enos.projetoSpring.screenmatch.models;

import Enos.projetoSpring.screenmatch.models.omdbData.EpisodeDetailedData;
import Enos.projetoSpring.screenmatch.models.omdbData.EpisodeSimpleData;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodes")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title",nullable = false,unique = true)
    private String title;
    @Column(name = "seasonNumber",nullable = false)
    private Integer seasonNumber;
    @Column(name = "episodeNumber",nullable = false)
    private Integer episodeNumber;
    @Column(name = "released")
    private LocalDate released;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "runtime")
    private Integer runtime;
    @Column(name = "plot")
    private String plot;
    @ManyToOne
    private Season season;

    public Episode(){}

    public Episode(EpisodeDetailedData episodeDetailedData) {
        this.title = episodeDetailedData.title();
        this.seasonNumber = episodeDetailedData.seasonNumber();
        this.episodeNumber = episodeDetailedData.episodeNumber();
        try {
            this.runtime = Integer.parseInt(episodeDetailedData.runtime().replaceAll("([^0-9]+)",""));
        } catch (NullPointerException e){
            this.runtime = null;
        }
        this.plot = episodeDetailedData.episodePlot();
        try {
            this.released = LocalDate.parse(episodeDetailedData.released());
            this.rating = Double.parseDouble(episodeDetailedData.rating());
        } catch (DateTimeParseException | NumberFormatException | NullPointerException e) {
            this.released = null;
        }
    }

    public Episode(Integer seasonNumber, EpisodeSimpleData episodeSimpleData){
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

    protected void setSeason(Season season){this.season = season; }

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
