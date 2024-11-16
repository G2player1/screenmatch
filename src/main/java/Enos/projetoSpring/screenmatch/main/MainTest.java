package Enos.projetoSpring.screenmatch.main;

import Enos.projetoSpring.screenmatch.Exceptions.DontHaveSeasonsException;
import Enos.projetoSpring.screenmatch.Exceptions.ResultNotFoundException;
import Enos.projetoSpring.screenmatch.Exceptions.WrongEpisodeDataException;
import Enos.projetoSpring.screenmatch.Exceptions.WrongTypeDataException;
import Enos.projetoSpring.screenmatch.controllers.ScreenMatchController;
import Enos.projetoSpring.screenmatch.enums.GenreEnum;
import Enos.projetoSpring.screenmatch.models.*;
import Enos.projetoSpring.screenmatch.models.omdbData.EpisodeDetailedData;
import Enos.projetoSpring.screenmatch.models.omdbData.TitleData;
import Enos.projetoSpring.screenmatch.repository.*;
import Enos.projetoSpring.screenmatch.service.ConsultChatGPT;
import com.theokanning.openai.OpenAiHttpException;

import java.util.*;

public class MainTest {

    private final ScreenMatchController screenMatchController = new ScreenMatchController();
    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=" + System.getenv("APIKEY_OMDB");
    private final String SEASON = "&season=";
    private final String EPISODE = "&episode=";
    private final ISerieRepository serieRepository;
    private final IMovieRepository movieRepository;
    private final ITitleRepository titleRepository;

    public MainTest(ISerieRepository serieRepository,IMovieRepository movieRepository,
                    ITitleRepository titleRepository){
        this.serieRepository = serieRepository;
        this.movieRepository = movieRepository;
        this.titleRepository = titleRepository;
    }

    public void main(){
        int i = -1;
        while(i != 0){
            Scanner sc = new Scanner(System.in);
            String menu = """
                    1 - Search Title
                    2 - Search Episode
                    3 - Show searched titles
                    4 - Search Titles by Genres
                    
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
                    this.printDataBaseTitles();
                    break;
                case 4:
                    this.getTitlesByGenres(sc);
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

    private void getTitlesByGenres(Scanner sc){
        List<Title> titleList = titleRepository.findAll();
        List<Genre> genreList = new ArrayList<>();
        List<GenreEnum> genreEnumList = new ArrayList<>(Arrays.asList(GenreEnum.values()));
        int op;
        while(true) {
            for(int j = 0;j < genreEnumList.size();j++){
                System.out.println((j + 1) + " - " + genreEnumList.get(j));
            }
            System.out.println("0 - left");
            System.out.println("Choose one genre: ");
            op = sc.nextInt();
            sc.nextLine();
            if(op == 0){
                break;
            }
            try {
                Genre genre = new Genre(genreEnumList.get(op - 1));
                genreList.add(genre);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        for (Title title : titleList){
            if(containsGenres(title,genreList)){
                System.out.println(title);
            }
        }
    }

    private boolean containsGenres(Title title,List<Genre> genreList){
        int i = 0;
        for (Genre genre : genreList){
            if(title.containsGenre(genre)){
                i++;
            }
        }
        return i == genreList.size();
    }

    private void printDataBaseTitles(){
        List<Title> titleList = titleRepository.findAll();
        titleList.forEach(System.out::println);
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
        try {
            return searchEpisodeDetailedData(serie,episode);
        } catch (WrongEpisodeDataException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Episode searchEpisodeByTitle(Serie serie,Scanner sc){
        System.out.println("Enter the episode title: ");
        String episodeTitle = sc.nextLine();
        Episode episode = serie.getEpisode(episodeTitle);
        try {
            return searchEpisodeDetailedData(serie,episode);
        } catch (WrongEpisodeDataException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Episode searchEpisodeDetailedData(Serie serie,Episode  episode){
        try {
            String address = getEpisodeAddress(serie, episode);
            EpisodeDetailedData episodeDetailedData = screenMatchController.getWebData(address,EpisodeDetailedData.class);
            return new Episode(episodeDetailedData);
        } catch (NullPointerException e){
            throw new WrongEpisodeDataException("the episode data is wrong!");
        }
    }

    private Serie searchSerie(String search){
        Serie serie = this.searchSerieInDB(search);
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

    private void searchTitle(Scanner sc){
        System.out.println("Enter a Title name: ");
        String search = sc.nextLine();
        Title title = searchTitleInDB(search);
        if(title == null){
            TitleData titleData = this.getTitleData(search);
            if(titleData.type() == null){
                throw new ResultNotFoundException("Result not found to: " + search);
            } else if(titleData.type().equalsIgnoreCase("movie")){
                this.showMovie(titleData);
            } else if (titleData.type().equalsIgnoreCase("series")){
                this.showSerie(titleData,sc);
            }
        } else {
            if(title.getType().equalsIgnoreCase("series")){
                Serie serie = searchSerieInDB(search);
                this.showSerie(serie,sc);
            } else if (title.getType().equalsIgnoreCase("movie")){
                Movie movie = searchMovieInDB(search);
                this.showMovie(movie);
            }
        }
    }

    private void showSerie(TitleData titleData,Scanner sc){
        try {
            Serie serie = new Serie(titleData);
            this.saveSerieInDB(serie);
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

    private void showSerie(Serie serie,Scanner sc){
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
    }

    private void showMovie(TitleData titleData){
        Movie movie = new Movie(titleData);
        this.saveMovieInDB(movie);
        System.out.println(movie);
        try {
            System.out.println(ConsultChatGPT.getTranslation(movie.toString(),"portuguese"));
        } catch (OpenAiHttpException e){
            System.out.println(e.getMessage());
        }
    }

    private void showMovie(Movie movie){
        System.out.println(movie);
        try {
            System.out.println(ConsultChatGPT.getTranslation(movie.toString(),"portuguese"));
        } catch (OpenAiHttpException e){
            System.out.println(e.getMessage());
        }
    }

    private void saveSerieInDB(Serie serie){
        List<Serie> serieList = serieRepository.findAll();
        for (Serie serie1 : serieList){
            if(serie.getTitle().equalsIgnoreCase(serie1.getTitle())){
                return;
            }
        }
        serieRepository.save(serie);
    }

    private void saveMovieInDB(Movie movie){
        List<Movie> movieList = movieRepository.findAll();
        for (Movie movie1 : movieList){
            if(movie.getTitle().equalsIgnoreCase(movie1.getTitle())){
                return;
            }
        }
        movieRepository.save(movie);
    }

    private Title searchTitleInDB(String search){
        List<Optional<Title>> optionalTitleList = titleRepository.findByTitleContainingIgnoreCase(search);
        if(optionalTitleList.isEmpty()){
            return null;
        }
        Optional<Title> optionalTitle = optionalTitleList.getFirst();
        return optionalTitle.orElse(null);
    }

    private Serie searchSerieInDB(String search){
        List<Optional<Serie>> optionalSerieList = serieRepository.findByTitleContainingIgnoreCase(search);
        if(optionalSerieList.isEmpty()){
            return null;
        }
        Optional<Serie> optionalSerie = optionalSerieList.getFirst();
        return optionalSerie.orElse(null);
    }

    private Movie searchMovieInDB(String search){
        List<Optional<Movie>> optionalMovieList = movieRepository.findByTitleContainingIgnoreCase(search);
        if(optionalMovieList.isEmpty()){
            return null;
        }
        Optional<Movie> optionalMovie = optionalMovieList.getFirst();
        return optionalMovie.orElse(null);
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
        return screenMatchController.getWebData(address,TitleData.class);
    }
}