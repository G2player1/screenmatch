package Enos.projetoSpring.screenmatch.models;

import java.util.ArrayList;
import java.util.List;

public class Season {
    private final List<Episode> episodeList;
    private final String title;
    private final Integer seasonNumber;

    public Season(SeasonData seasonData){
        episodeList = new ArrayList<>();
        this.title = seasonData.title();
        this.seasonNumber = seasonData.seasonNumber();
        addEpisodeData(seasonData.episodes());
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
