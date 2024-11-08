package Enos.projetoSpring.screenmatch.models;

import Enos.projetoSpring.screenmatch.models.omdbData.EpisodeSimpleData;
import Enos.projetoSpring.screenmatch.models.omdbData.SeasonData;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seasons")
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "seasonNumber",nullable = false)
    private Integer seasonNumber;
    @OneToMany(mappedBy = "season",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Episode> episodeList;
    @ManyToOne
    private Serie serie;

    public Season(){}

    public Season(SeasonData seasonData){
        episodeList = new ArrayList<>();
        this.title = seasonData.title();
        this.seasonNumber = seasonData.seasonNumber();
        addEpisodeData(seasonData.episodes());
    }

    protected void setSerie(Serie serie){
        this.serie = serie;
    }

    private void addEpisodeData(List<EpisodeSimpleData> episodeList){
        for(EpisodeSimpleData episodeSimpleData : episodeList){
            Episode episode = new Episode(this.seasonNumber,episodeSimpleData);
            this.addEpisode(episode);
        }
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public Integer getEpisodesNumber() {
        return episodeList.size();
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public String getTitle() {
        return title;
    }

    public void addEpisode(Episode episode){
        if(episode != null){
            for (Episode e : episodeList){
                if (e.getTitle().equalsIgnoreCase(episode.getTitle())){
                    return;
                }
            }
            episode.setSeason(this);
            this.episodeList.add(episode);
        }
    }



    protected Episode getEpisode(String title){
        for (Episode episode: episodeList){
            if(episode.getTitle().equalsIgnoreCase(title)){
                return episode;
            }
            if(episode.getTitle().contains(title)){
                return episode;
            }
        }
        return null;
    }

    protected Episode getEpisode(int episodeNumber){
        for (Episode episode: episodeList){
            if(episode.getEpisodeNumber() == episodeNumber){
                return episode;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Season title: " + title + '\n' +
                "Season Number: " + seasonNumber + '\n' +
                "Episode(s): \n" + episodeList + "\n";
    }
}
