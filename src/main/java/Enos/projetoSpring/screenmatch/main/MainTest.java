package Enos.projetoSpring.screenmatch.main;

import Enos.projetoSpring.screenmatch.Exceptions.DontHaveSeasonsException;
import Enos.projetoSpring.screenmatch.Exceptions.ResultNotFoundException;
import Enos.projetoSpring.screenmatch.Exceptions.WrongTypeDataException;
import Enos.projetoSpring.screenmatch.controllers.ScreenMatchController;
import Enos.projetoSpring.screenmatch.models.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MainTest {

    private final ScreenMatchController screenMatchController = new ScreenMatchController();
    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=34451d52";
    private final String SEASON = "&season=";
    private final String EPISODE = "&episode=";
    private final List<Movie> movieList = new ArrayList<>();
    private final List<Serie> serieList = new ArrayList<>();

    public void main(){
        int i = -1;
        while(i != 0){
            Scanner sc = new Scanner(System.in);
            String menu = """
                    1 - Search Title
                    2 - Search Episode
                    3 - Show searched series
                    4 - Show searched movies
                    
                    0 - left
                    Choose an option:
                    """;
            System.out.println(menu);
            int op = -1;
            try {
                op = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Insert a integer number!");
            }
            switch (op) {
                case 1:
                    try {
                        this.searchTitle(sc);
                    } catch (ResultNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        this.searchEpisode(sc);
                    } catch (ResultNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    serieList.forEach(System.out::println);
                    break;
                case 4:
                    movieList.forEach(System.out::println);
                    break;
                case 0:
                    i = 0;
                    break;
                default:
                    System.out.println("Invalid value");
                    break;
            }
        }
    }

    private void searchEpisode(Scanner sc){
        System.out.println("Enter the Serie name: ");
        String search = sc.nextLine();
        Serie serie = this.searchSerie(search);
        String msg = """
                1 - Search Episode by Number
                2 - Search Episode by Title
                
                Choose an option:
                """;
        System.out.println(msg);
        int option = sc.nextInt();
        sc.nextLine();
        Episode episode;
        switch (option){
            case 1:
                episode = searchEpisodeByNumber(serie,sc);
                System.out.println(episode);
                break;
            case 2:
                episode = searchEpisodeByTitle(serie,sc);
                System.out.println(episode);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private Episode searchEpisodeByNumber(Serie serie,Scanner sc){
        System.out.println("Enter the season number: ");
        int seasonNumber = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the episode number: ");
        int episodeNumber = sc.nextInt();
        sc.nextLine();
        Episode episode = serie.getEpisode(seasonNumber,episodeNumber);
        return searchEpisodeDetailedData(serie,episode);
    }

    private Episode searchEpisodeByTitle(Serie serie,Scanner sc){
        System.out.println("Enter the episode title: ");
        String episodeTitle = sc.nextLine();
        Episode episode = serie.getEpisode(episodeTitle);
        return searchEpisodeDetailedData(serie,episode);
    }

    private Episode searchEpisodeDetailedData(Serie serie,Episode  episode){
        String address = getEpisodeAddress(serie,episode);
        EpisodeDetailedData episodeDetailedData = screenMatchController.getEpisodeDetailedData(address);
        return new Episode(episodeDetailedData);
    }

    private Serie searchSerie(String search){
        Serie serie = this.searchSerieInSerieList(search);
        if(serie != null){
            return serie;
        }
        return this.searchSerieInWeb(search);
    }

    private Serie searchSerieInWeb(String search){
        TitleData titleData = this.getTitleData(search);
        if(titleData.type() == null){
            throw new ResultNotFoundException("Result not found to: " + search);
        } else if(titleData.type().equalsIgnoreCase("movie")){
            throw new WrongTypeDataException("The search isin't a serie");
        } else if (titleData.type().equalsIgnoreCase("series")){
            try {
                return new Serie(titleData);
            } catch (DontHaveSeasonsException e) {
                throw new WrongTypeDataException("The search isin't a serie");
            }
        }
        return null;
    }

    private Serie searchSerieInSerieList(String search){
        for (Serie serie : serieList){
            if(serie.getTitle().equalsIgnoreCase(search)){
                return serie;
            }
        }
        return null;
    }

    private void searchTitle(Scanner sc){
        System.out.println("Enter a Title name: ");
        String search = sc.nextLine();
        TitleData titleData = this.getTitleData(search);
        if(titleData.type() == null){
            throw new ResultNotFoundException("Result not found to: " + search);
        } else if(titleData.type().equalsIgnoreCase("movie")){
            this.showMovie(titleData);
        } else if (titleData.type().equalsIgnoreCase("series")){
            this.showSerie(titleData,sc);
        }
    }

    private void showSerie(TitleData titleData,Scanner sc){
        try {
            Serie serie = new Serie(titleData);
            this.serieExistsInList(serie);
            System.out.println(serie);
            System.out.println("Do you want to see the seasons?(s/n)");
            String response = sc.nextLine();
            if(response.equalsIgnoreCase("s")){
                System.out.println(serie.printSeasons());
            }
            System.out.println("Do you want to see the detailed ratings?(s/n)");
            response = sc.nextLine();
            if(response.equalsIgnoreCase("s")){
                System.out.println(serie.printDetailedRating());
            }
        } catch (DontHaveSeasonsException e) {
            System.out.println(e.getMessage());
            this.showMovie(titleData);
        }

    }

    private void serieExistsInList(Serie serie){
        for (Serie serie1 : serieList){
            if (serie.getTitle().equalsIgnoreCase(serie1.getTitle())){
                return;
            }
        }
        serieList.add(serie);
    }

    private void showMovie(TitleData titleData){
        Movie movie = new Movie(titleData);
        System.out.println(movie);
        this.movieExistsInMovieList(movie);
    }

    private void movieExistsInMovieList(Movie movie){
        for(Movie movie1 : movieList){
            if (movie1.getTitle().equalsIgnoreCase(movie.getTitle())){
                return;
            }
        }
        movieList.add(movie);
    }

    private String getTitleAddress(String search){
        return ADDRESS + search.replace(" ","+") + APIKEY;
    }

    private String getEpisodeAddress(Serie serie, Episode episode){
        return ADDRESS + serie.getTitle().replace(" ","+") +
                SEASON + episode.getSeasonNumber() +
                EPISODE + episode.getEpisodeNumber() + APIKEY;
    }

    private TitleData getTitleData(String search){
        String address = getTitleAddress(search);
        return screenMatchController.getTitleDataWeb(address);
    }
}
