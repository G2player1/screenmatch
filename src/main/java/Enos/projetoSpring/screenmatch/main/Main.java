package Enos.projetoSpring.screenmatch.main;

import Enos.projetoSpring.screenmatch.Exceptions.ResultNotFoundException;
import Enos.projetoSpring.screenmatch.controllers.ScreenMatchController;
import Enos.projetoSpring.screenmatch.models.*;
import Enos.projetoSpring.screenmatch.service.ConsumeAPI;
import Enos.projetoSpring.screenmatch.service.ConvertData;

import java.util.*;

public class Main {

    private final ScreenMatchController screenMatchController = new ScreenMatchController();

    public void showMenu(){
        int i = 0;
        while(i != 1){
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter the name of the title to search: ");
            String search = scan.nextLine();
            String address = "https://www.omdbapi.com/?t=" + search.replace(" ","+") + "&apikey=34451d52";
            TitleData titleData = screenMatchController.getTitleWebData(address);
            if(titleData.type() == null){
                throw new ResultNotFoundException("Result not found to: " + search);
            }
            String type = titleData.type();
            if (type.equals("series")) {
                Serie serie = new Serie(titleData);
                System.out.println(serie);
                System.out.println(serie.printDetailedRating());

                System.out.println("Wish to search a episode?(s/n)");
                String test = scan.next();
                if (test.equalsIgnoreCase("s")){
                    System.out.println("Write a episode name: ");
                    test = scan.next();
                    System.out.println(serie.getEpisode(test));
                } else {
                    i = 1;
                }
            } else if(type.equals("movie")){
                Title title = new Title(titleData);
                System.out.println(title);
            }

        }
    }

}
