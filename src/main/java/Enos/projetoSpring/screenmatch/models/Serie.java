package Enos.projetoSpring.screenmatch.models;

import Enos.projetoSpring.screenmatch.service.ConsumeAPI;
import Enos.projetoSpring.screenmatch.service.ConvertData;

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

    public Serie(TitleData titleData){
        super(titleData);
        seasonList = new ArrayList<Season>();
        this.totalSeasons = titleData.totalSeasons();
        this.addSeasonData();
        this.totalEpisodes = getTotalEpisodes();
    }

    private void addSeasonData(){
        ConsumeAPI consumeAPI = new ConsumeAPI();
        for (int i = 1; i <= totalSeasons;i++){
            String address = "https://www.omdbapi.com/?t=" + this.getTitle().replace(" ","+") +
                    "&season=" + i + "&apikey=34451d52";
            String json = consumeAPI.getData(address);
            SeasonData seasonData = new ConvertData().getData(json,SeasonData.class);
            Season season = new Season(seasonData);
            this.addSeason(season);
        }
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

    public String printSeasons(){
        String msg = "";
        for (Season season: seasonList){
            msg += season.toString();
        }
        return msg;
    }

    @Override
    public String toString() {
        return "Poster: " + getPoster() + '\n' +
                "Title: " + getTitle() + "\n" +
                "Genre: " + getGenre() + '\n' +
                "Rating: " + getRating() + "\n" +
                "Total Votes: " + getTotalVotes() + "\n" +
                "Year: " + getYear() + "\n" +
                "Released: " + getReleased() + '\n' +
                "Runtime: " + getRuntime() + "\n" +
                "Awards: " + getAwards() + '\n' +
                "Language: " + getLanguage() + '\n' +
                "Type: " + getType() + '\n' +
                "Sinpose: " + getSinpose() + '\n' +
                "Employee(s): \n" + printEmployees() +
                "Seasons: \n" + printSeasons();
    }
}
