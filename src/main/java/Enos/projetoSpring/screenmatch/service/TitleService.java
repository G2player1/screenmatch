package Enos.projetoSpring.screenmatch.service;

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
        return convertTitleDTOList(titleRepository.findAll());
    }

    public List<TitleDTO> getTitlesTop5(){
        return convertTitleDTOList(titleRepository.findTop5ByOrderByRating());
    }

    public SerieDTO getSerieById(Long id){
        return convertSerieDTO(serieRepository.findSerieById(id));
    }

    public List<SerieDTO> getAllSeries(){
        serieRepository.findAll().forEach(serie -> System.out.println(serie.getId()));
        return convertSerieDTOList(serieRepository.findAll());
    }

    public List<SerieDTO> getSeriesTop5(){
        return convertSerieDTOList(serieRepository.findTop5ByOrderByRating());
    }

    public List<SerieDTO> getSeriesByReleaseDateTest(){
        return convertSerieDTOList(serieRepository.findTop5ByOrderByReleased());
    }

    public List<SerieDTO> getSeriesByReleaseDate(){
        List<Serie> serieList = serieRepository.findAll();
        List<Serie> seriesByRelease = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusMonths(3);
        boolean b = false;
        for (Serie serie : serieList){
            b = false;
            System.out.println(serie.getId());
            for (Season season: serie.getSeasonList()){
                for (Episode episode: season.getEpisodeList()){
                    if(episode.getReleased() != null) {
                        if (episode.getReleased().isAfter(localDate)) {
                            if(!seriesByRelease.contains(serie)){
                                seriesByRelease.add(serie);
                                b = true;
                                break;
                            }
                        }
                    }
                }
                if(b){break;}
            }
        }
        return convertSerieDTOList(seriesByRelease);
    }

    public List<SerieDTO> convertSerieDTOList(List<Serie> serieList){
        return serieList.stream()
                .map(this::convertSerieDTO).toList();
    }

    public List<TitleDTO> convertTitleDTOList(List<Title> titleList){
        return titleList.stream()
                .map(this::converTitleDTO).toList();
    }

    public SerieDTO convertSerieDTO(Serie s){
        return new SerieDTO(
                s.getId() , s.getPoster() ,s.getTitle(),
                s.getRuntime(), s.getSinopse(), s.getReleased(),
                s.getAwards(), s.getLanguage(), s.getYear(),
                s.getRating(), s.getTotalVotes(),
                s.printEmployees(),
                s.printGenres()
        );
    }

    public TitleDTO converTitleDTO(Title t){
        return new TitleDTO(
                t.getId() , t.getPoster() ,t.getTitle(),
                t.getRuntime(), t.getSinopse(), t.getReleased(),
                t.getAwards(), t.getLanguage(), t.getYear(),
                t.getRating(), t.getTotalVotes(),
                t.printEmployees(),
                t.printGenres()
        );
    }
}
