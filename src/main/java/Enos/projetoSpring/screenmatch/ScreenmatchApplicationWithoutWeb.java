//package Enos.projetoSpring.screenmatch;
//
//import Enos.projetoSpring.screenmatch.main.MainTestWithoutWeb;
//import Enos.projetoSpring.screenmatch.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScreenmatchApplicationWithoutWeb implements CommandLineRunner {
//
//	@Autowired
//	private ISerieRepository serieRepository;
//	@Autowired
//	private IMovieRepository movieRepository;
//	@Autowired
//	private ITitleRepository titleRepository;
//
//	public static void main(String[] args) {
//		SpringApplication.run(ScreenmatchApplicationWithoutWeb.class, args);
//	}
//
//	@Override
//	public void run(String... args) {
//		MainTestWithoutWeb mainTest = new MainTestWithoutWeb(serieRepository, movieRepository, titleRepository);
//		mainTest.main();
//	}
//}
