package Enos.projetoSpring.screenmatch;

import Enos.projetoSpring.screenmatch.Exceptions.CantGetDataException;
import Enos.projetoSpring.screenmatch.Exceptions.ResultNotFoundException;
import Enos.projetoSpring.screenmatch.main.MainTest;
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
		MainTest mainTest = new MainTest();
		mainTest.main();

	}
}
