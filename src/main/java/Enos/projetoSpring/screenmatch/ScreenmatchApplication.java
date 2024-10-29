package Enos.projetoSpring.screenmatch;

import Enos.projetoSpring.screenmatch.Exceptions.CantGetDataException;
import Enos.projetoSpring.screenmatch.Exceptions.ResultNotFoundException;
import Enos.projetoSpring.screenmatch.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			Main main = new Main();
			main.showMenu();
		} catch (ResultNotFoundException | CantGetDataException e){
			System.out.println(e.getMessage());
		}

	}
}
