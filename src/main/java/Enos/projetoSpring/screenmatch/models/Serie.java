package Enos.projetoSpring.screenmatch.models;

import Enos.projetoSpring.screenmatch.Exceptions.DontHaveSeasonsException;
import Enos.projetoSpring.screenmatch.service.ConsumeAPI;
import Enos.projetoSpring.screenmatch.service.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

public class Serie extends Title{
    private final List<Season> seasonList;

    public Serie(TitleData titleData){
        super(titleData);
        seasonList = new ArrayList<>();
        if(titleData.totalSeasons().equalsIgnoreCase("n/a")){
            throw new DontHaveSeasonsException("this serie does not have any seasons");
        }
        this.addSeasonData(Integer.parseInt(titleData.totalSeasons()));
    }

    private void addSeasonData(int seasonsNumber){
        ConsumeAPI consumeAPI = new ConsumeAPI();
        for (int i = 1; i <= seasonsNumber;i++){
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

    public DoubleSummaryStatistics getEpisodeSummaryStatistics(){
        List<Episode> episodeList = new ArrayList<>();
        this.getSeasonList().forEach(season -> episodeList.addAll(season.getEpisodeList()));
        return episodeList.stream()
                .filter(episode -> episode.getRating() != null)
                .filter(episode -> episode.getRating() != 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));
    }

    public DoubleSummaryStatistics getSeasonSummaryStatistics(){
        return getAverageRatingPerSeason().values().stream().collect(Collectors.summarizingDouble(value -> value));
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

    public void addSeason(Season season){
        if(season != null){
            for (Season s: seasonList){
                if(Objects.equals(s.getSeasonNumber(), season.getSeasonNumber())){
                    return;
                }
            }
            this.seasonList.add(season);
        }
    }

    public Season getSeason(String title){
        for (Season season : seasonList){
            if(season.getTitle().equalsIgnoreCase(title)){
                return season;
            }
        }
        return null;
    }

    public Episode getEpisode(String episodeTitle){
        for (Season season : seasonList){
            if(season.getEpisode(episodeTitle) != null){
                return season.getEpisode(episodeTitle);
            }
        }
        return null;
    }

    public Episode getEpisode(Integer seasonNumber,Integer episodeNumber){
        for (Season season : seasonList){
            if(Objects.equals(season.getSeasonNumber(), seasonNumber)){
                return season.getEpisode(episodeNumber);
            }
        }
        return null;
    }

    public String printSeasons(){
        StringBuilder msg = new StringBuilder();
        for (Season season: seasonList){
            msg.append(season.toString());
        }
        return msg.toString();
    }

    public Map<Integer,Double> getAverageRatingPerSeason(){
        List<Episode> episodeList = new ArrayList<>();
        this.getSeasonList().forEach(season -> episodeList.addAll(season.getEpisodeList()));
        return episodeList.stream().filter(e -> e.getRating() != null).collect(Collectors.groupingBy(Episode::getSeasonNumber,Collectors.averagingDouble(Episode::getRating)));
    }

    public String printAverageRatingPerSeason(){
        Map<Integer,Double> seasonRating = getAverageRatingPerSeason();
        StringBuilder result = new StringBuilder();
        for(int i = 1;i <= seasonRating.size();i++){
            result.append("Season: ").append(i).append(" has rating: ").append(seasonRating.get(i)).append("\n");
        }
        return result.toString();
    }

    public String printDetailedRating(){
        return getTitle() + " ratings: \n" +
                "Serie general rating: " + getRating() + "\n" +
                "Average seasons rating: " + this.getSeasonSummaryStatistics().getAverage() + "\n" +
                "Max season rating: " + this.getSeasonSummaryStatistics().getMax() + "\n" +
                "Min season rating: " + this.getSeasonSummaryStatistics().getMin() + "\n" +
                "Average rating per season: \n" + printAverageRatingPerSeason() +
                "Average episodes rating: " + this.getEpisodeSummaryStatistics().getAverage() + "\n" +
                "Max episode rating: " + this.getEpisodeSummaryStatistics().getMax() + "\n" +
                "Min episode rating: " + this.getEpisodeSummaryStatistics().getMin() + "\n";
    }

    @Override
    public String toString() {
        return "Poster: " + getPoster() + '\n' +
                "Title: " + getTitle() + "\n" +
                "Genres: " + printGenres() + '\n' +
                "Rating: " + getRating() + "\n" +
                "Total Votes: " + getTotalVotes() + "\n" +
                "Year: " + getYear() + "\n" +
                "Released: " + getReleased() + '\n' +
                "Runtime: " + getRuntime() + "\n" +
                "Awards: " + getAwards() + '\n' +
                "Language: " + getLanguage() + '\n' +
                "Type: " + getType() + '\n' +
                "Sinpose: " + getSinpose() + '\n' +
                "Employee(s): \n" + printEmployees();
    }
}
