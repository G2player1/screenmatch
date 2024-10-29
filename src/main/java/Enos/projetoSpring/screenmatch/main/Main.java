package Enos.projetoSpring.screenmatch.main;

import Enos.projetoSpring.screenmatch.Exceptions.ResultNotFoundException;
import Enos.projetoSpring.screenmatch.models.*;
import Enos.projetoSpring.screenmatch.service.ConsumeAPI;
import Enos.projetoSpring.screenmatch.service.ConvertData;

import java.util.*;

public class Main {

    private final ConsumeAPI consumeAPI = new ConsumeAPI();

    public void showMenu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the name of the series to search: ");
        String search = scan.nextLine();
        scan.close();
        String ADDRESS = "https://www.omdbapi.com/?t=";
        String APIKEY = "&apikey=34451d52";
        TitleData titleData = this.searchTitle(ADDRESS + search.replace(" ","+") + APIKEY);
        if(titleData.type() == null){
            throw new ResultNotFoundException("Result not found to: " + search);
        }
        String type = titleData.type();
        if (type.equals("series")) {
            Serie serie = new Serie(titleData);
            System.out.println(serie);
            System.out.println(serie.printDetailedRating());
        } else if(type.equals("movie")){
            Title title = new Title(titleData);
            System.out.println(title);
        }
    }

    public TitleData searchTitle(String address){
        String jsonResponse = consumeAPI.getData(address);
        ConvertData convertData = new ConvertData();
        return convertData.getData(jsonResponse, TitleData.class);
    }
}
