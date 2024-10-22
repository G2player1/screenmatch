package Enos.projetoSpring.screenmatch.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Serie extends Title{
    private List<Season> seasonList;
    private Integer totalSeasons;
    private Integer totalEpisodes;

    public Serie(String title, Integer year, String genre, String language, Double rating, String sinpose, String awards, Integer totalVotes) {
        super(title, year, genre, language, rating, sinpose, awards, totalVotes);
        seasonList = new ArrayList<Season>();
        this.totalEpisodes = getTotalEpisodes();
        this.totalSeasons = getTotalSeasons();
    }

    public List<Season> getSeasonList() {
        return seasonList;
    }

    public Integer getTotalEpisodes() {
        int i = 0;
        for(Season season : seasonList){
            i += season.getEpisodesNumber();
        }
        return i;
    }

    public Integer getTotalSeasons() {
        return seasonList.size();
    }

    public String addSeason(Season season){
        if(season != null){
            for (Season s: seasonList){
                if(Objects.equals(s.getSeasonNumber(), season.getSeasonNumber())){
                    return "Season already exists";
                }
            }
            this.seasonList.add(season);
            this.totalSeasons++;
            this.totalEpisodes = getTotalEpisodes();
            return "Season has been added!";
        }
        return "Season is null";
    }

    public Season getSeason(String title){
        for (Season season : seasonList){
            if(season.getTitle().equalsIgnoreCase(title)){
                return season;
            }
        }
        return null;
    }

    public String addEpisode(String seasonTitle, Episode episode){
        for (Season season : seasonList){
            if(season.getTitle().equalsIgnoreCase(seasonTitle)){
                String msg = season.addEpisode(episode);
                this.totalEpisodes = getTotalEpisodes();
                return msg;
            }
        }
        return "Season does not exist";
    }

    public String addEpisode(int seasonNumber, Episode episode){
        for (Season season : seasonList){
            if(season.getSeasonNumber() == seasonNumber){
                String msg = season.addEpisode(episode);
                this.totalEpisodes = getTotalEpisodes();
                return msg;
            }
        }
        return "Season does not exist";
    }

    public Episode getEpisode(String episodeTitle){
        for (Season season : seasonList){
            if(season.getEpisode(episodeTitle) != null){
                return season.getEpisode(episodeTitle);
            }
        }
        return null;
    }
}
