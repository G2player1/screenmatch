package Enos.projetoSpring.screenmatch.models;

import java.util.ArrayList;
import java.util.List;

public class Season {
    private List<Episode> episodeList;
    private String title;
    private Integer episodesNumber;
    private Integer seasonNumber;

    public Season(String title, Integer seasonNumber) {
        episodeList = new ArrayList<Episode>();
        this.title = title;
        this.seasonNumber = seasonNumber;
    }

    public Season(SeasonData seasonData){
        episodeList = new ArrayList<Episode>();
        this.title = seasonData.title();
        this.seasonNumber = seasonData.seasonNumber();
        addEpisodeData(seasonData.episodes());
        this.episodesNumber = seasonData.episodes().size();
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

    public String addEpisode(Episode episode){
        if(episode != null){
            for (Episode e : episodeList){
                if (e.getTitle().equalsIgnoreCase(episode.getTitle())){
                    return "Episode already exists";
                }
            }
            this.episodeList.add(episode);
            this.episodesNumber = getEpisodesNumber();
            return "Episode has been added!";
        } else {
            return "Episode is null";
        }
    }



    protected Episode getEpisode(String title){
        for (Episode episode: episodeList){
            if(episode.getTitle().equalsIgnoreCase(title)){
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
