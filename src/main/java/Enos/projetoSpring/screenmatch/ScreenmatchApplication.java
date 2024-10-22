package Enos.projetoSpring.screenmatch;

import Enos.projetoSpring.screenmatch.models.Title;
import Enos.projetoSpring.screenmatch.models.TitlesData;
import Enos.projetoSpring.screenmatch.service.ConsumeAPI;
import Enos.projetoSpring.screenmatch.service.ConvertData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.ref.SoftReference;
import java.util.Calendar;
import java.util.Date;
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
		System.out.println(jsonResponse);

		ConvertData convertData = new ConvertData();
		TitlesData titlesData = convertData.getData(jsonResponse, TitlesData.class);
		Title title = new Title(titlesData);
		System.out.println(title);
	}
}
