package Enos.projetoSpring.screenmatch.main;

import Enos.projetoSpring.screenmatch.Exceptions.DontHaveSeasonsException;
import Enos.projetoSpring.screenmatch.Exceptions.ResultNotFoundException;
import Enos.projetoSpring.screenmatch.controllers.ScreenMatchController;
import Enos.projetoSpring.screenmatch.models.*;

import java.util.*;

public class Main {

    private final ScreenMatchController screenMatchController = new ScreenMatchController();
    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=34451d52";
    private final String SEASON = "&season=";
    private final String EPISODE = "&episode=";
    private List<Serie> serieList = new ArrayList<>();
    private List<Title> titleList = new ArrayList<>();

    public void showMenu(){
        int i = -1;
        while(i != 0){
            Scanner scan = new Scanner(System.in);
            String menu = """
                    1 - Search Title
                    2 - Search Episode
                    3 - Show searched series
                    
                    0 - left
                    """;
            System.out.println(menu);
            i = scan.nextInt();
            switch (i) {
                case 1:
                    System.out.println("Enter the name of the title to search: ");
                    String search = scan.next();
                    String address = ADDRESS + search.replace(" ","+") + APIKEY;
                    TitleData titleData = screenMatchController.getTitleWebData(address);
                    if(titleData.type() == null){
                        throw new ResultNotFoundException("Result not found to: " + search);
                    }
                    String type = titleData.type();
                    if (type.equals("series")) {
                        showSerie(titleData,scan);
                    } else if(type.equals("movie")){
                        showMovie(titleData,scan);
                    }
                    break;
                case 2:
                    System.out.println("Enter a serie name to search: ");
                    String serieTitle = scan.next();
                    Serie serie = serieList.stream().filter(s -> s.getTitle().equalsIgnoreCase(serieTitle)).findFirst().get();
                    System.out.println("Enter the episode title: ");
                    String episodeTitle = scan.next();
                    Episode episode = serie.getEpisode(episodeTitle);
                    System.out.println(episode);
                    break;
                case 3:
                    serieList.forEach(System.out::println);
                    break;
            }
        }
    }

    private void showSerie(TitleData titleData,Scanner scan){
        try {
            Serie serie = new Serie(titleData);
            serieList.add(serie);
            System.out.println(serie);
            System.out.println("Want to search a episode?(s/n)");
            String c = scan.next();
            if(c.equalsIgnoreCase("s")){
                System.out.println("Write a episode title: ");
                c = scan.next();
                scan.next();
                Episode episode = serie.getEpisode(c);
                String address = ADDRESS + serie.getTitle().replace(" ","+") +
                        SEASON + episode.getSeasonNumber() +
                        EPISODE + episode.getEpisodeNumber() + APIKEY;
                System.out.println(new Episode(screenMatchController.getEpisodeDetailedData(address)));
            }
        } catch (DontHaveSeasonsException e){
            System.out.println(e.getMessage());
            showMovie(titleData,scan);
        }
    }

    private void showMovie(TitleData titleData,Scanner scan){
        Movie movie = new Movie(titleData);
        System.out.println(movie);
    }

}
