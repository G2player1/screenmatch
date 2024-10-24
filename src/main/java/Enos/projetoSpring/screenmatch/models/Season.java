package Enos.projetoSpring.screenmatch.models;

import Enos.projetoSpring.screenmatch.service.ConsumeAPI;
import Enos.projetoSpring.screenmatch.service.ConvertData;

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
        this.episodesNumber = seasonData.episodes().size();
        addEpisodeData(seasonData.episodes());
    }

    private void addEpisodeData(List<EpisodeSimpleData> episodeList){
        ConsumeAPI consumeAPI = new ConsumeAPI();
        for(EpisodeSimpleData episodeSimpleData : episodeList){
            String address = "https://www.omdbapi.com/?t=" + this.getTitle().replace(" ","+") +
                    "&season=" + this.getSeasonNumber() +
                    "&episode=" + episodeSimpleData.episodeNumber() + "&apikey=34451d52";
            String json = consumeAPI.getData(address);
            EpisodeDetailedData episodeDetailedData = new ConvertData().getData(json, EpisodeDetailedData.class);
            Episode episode = new Episode(episodeDetailedData);
            this.addEpisode(episode);
        }
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public Integer getEpisodesNumber() {
        return episodesNumber;
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
        }
        return null;
    }

    @Override
    public String toString() {
        return "Season title: " + title + '\n' +
                "Season Number: " + seasonNumber + '\n' +
                "Episode(s): \n" + episodeList;
    }
}
