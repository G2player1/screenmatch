package Enos.projetoSpring.screenmatch;

import Enos.projetoSpring.screenmatch.models.Serie;
import Enos.projetoSpring.screenmatch.models.Title;
import Enos.projetoSpring.screenmatch.models.TitleData;
import Enos.projetoSpring.screenmatch.service.ConsumeAPI;
import Enos.projetoSpring.screenmatch.service.ConvertData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner sc = new Scanner(System.in);
		String search = sc.nextLine();
		sc.close();

		String address = "https://www.omdbapi.com/?t=" + search.replace(" ","+") + "&apikey=34451d52";
		String jsonResponse = new ConsumeAPI().getData(address);

		ConvertData convertData = new ConvertData();
		TitleData titleData = convertData.getData(jsonResponse, TitleData.class);

		String type = titleData.type();

        if (type.equals("series")) {
            Serie serie = new Serie(titleData);
            System.out.println(serie);
        } else if(type.equals("movie")){
			Title title = new Title(titleData);
			System.out.println(title);
		}
	}
}
