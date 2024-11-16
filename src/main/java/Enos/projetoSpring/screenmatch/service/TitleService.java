package Enos.projetoSpring.screenmatch.service;

import Enos.projetoSpring.screenmatch.dto.EmployeeDTO;
import Enos.projetoSpring.screenmatch.dto.GenreDTO;
import Enos.projetoSpring.screenmatch.dto.SerieDTO;
import Enos.projetoSpring.screenmatch.dto.TitleDTO;
import Enos.projetoSpring.screenmatch.models.Episode;
import Enos.projetoSpring.screenmatch.models.Season;
import Enos.projetoSpring.screenmatch.models.Serie;
import Enos.projetoSpring.screenmatch.models.Title;
import Enos.projetoSpring.screenmatch.repository.ISerieRepository;
import Enos.projetoSpring.screenmatch.repository.ITitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TitleService {

    @Autowired
    private ISerieRepository serieRepository;
    @Autowired
    private ITitleRepository titleRepository;

    public List<TitleDTO> getAllTitles(){
        return convertTitleDTO(titleRepository.findAll());
    }

    public List<TitleDTO> getTitlesTop5(){
        return convertTitleDTO(titleRepository.findTop5ByOrderByRating());
    }

    public List<SerieDTO> getAllSeries(){
        return convertSerieDTO(serieRepository.findAll());
    }

    public List<SerieDTO> getSeriesTop5(){
        return convertSerieDTO(serieRepository.findTop5ByOrderByRating());
    }

    public List<SerieDTO> getSeriesByReleaseDateTest(){
        return convertSerieDTO(serieRepository.findTop5ByOrderByReleased());
    }

    public List<SerieDTO> getSeriesByReleaseDate(){
        List<Serie> serieList = serieRepository.findAll();
        List<Serie> seriesByRelease = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusMonths(3);
        for (Serie serie : serieList){
            for (Season season: serie.getSeasonList()){
                for (Episode episode: season.getEpisodeList()){
                    if(episode.getReleased() != null) {
                        if (episode.getReleased().isAfter(localDate)) {
                            if(!seriesByRelease.contains(serie)){
                                seriesByRelease.add(serie);
                            }
                        }
                    }
                }
            }
        }
        return convertSerieDTO(seriesByRelease);
    }

    public List<SerieDTO> convertSerieDTO(List<Serie> serieList){
        return serieList.stream()
                .map(s -> new SerieDTO(
                        s.getId() , s.getPoster() ,s.getTitle(),
                        s.getRuntime(), s.getSinopse(), s.getReleased(),
                        s.getAwards(), s.getLanguage(), s.getYear(),
                        s.getRating(), s.getTotalVotes(),
                        s.getEmployeeList().stream().map(e -> new EmployeeDTO(
                                e.getId(),
                                e.getName(),
                                e.getPosition())).toList(),
                        s.getGenreList().stream().map(g -> new GenreDTO(
                                g.getId(),
                                g.getGenre()
                        )).toList()
                )).toList();
    }

    public List<TitleDTO> convertTitleDTO(List<Title> titleList){
        return titleList.stream()
                .map(t -> new TitleDTO(
                        t.getId() , t.getPoster() ,t.getTitle(),
                        t.getRuntime(), t.getSinopse(), t.getReleased(),
                        t.getAwards(), t.getLanguage(), t.getYear(),
                        t.getRating(), t.getTotalVotes(),
                        t.getEmployeeList().stream().map(e -> new EmployeeDTO(
                                e.getId(),
                                e.getName(),
                                e.getPosition())).toList(),
                        t.getGenreList().stream().map(g -> new GenreDTO(
                                g.getId(),
                                g.getGenre()
                        )).toList()
                )).toList();
    }
}
