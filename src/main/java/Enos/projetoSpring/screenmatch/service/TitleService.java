package Enos.projetoSpring.screenmatch.service;

import Enos.projetoSpring.screenmatch.dto.EpisodeDTO;
import Enos.projetoSpring.screenmatch.dto.SerieDTO;
import Enos.projetoSpring.screenmatch.enums.GenreEnum;
import Enos.projetoSpring.screenmatch.models.Episode;
import Enos.projetoSpring.screenmatch.models.Genre;
import Enos.projetoSpring.screenmatch.models.Season;
import Enos.projetoSpring.screenmatch.models.Serie;
import Enos.projetoSpring.screenmatch.repository.ISerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TitleService {

    @Autowired
    private ISerieRepository serieRepository;

    public List<EpisodeDTO> getEpisodesById(Long id){
        Serie serie = serieRepository.findSerieById(id);
        List<Episode> episodeList = new ArrayList<>();
        serie.getSeasonList().forEach(season -> episodeList.addAll(season.getEpisodeList()));
        return convertEpisodeDTOList(episodeList);
    }

    public List<EpisodeDTO> getEpisodesBySeason(Long id, Integer seasonNumber){
        Serie serie = serieRepository.findSerieById(id);
        List<Episode> episodeList = new ArrayList<>();
        serie.getSeasonList()
                .stream()
                .filter(season -> season.getSeasonNumber().equals(seasonNumber))
                .forEach(season -> episodeList.addAll(season.getEpisodeList()));
        return convertEpisodeDTOList(episodeList);
    }

    public SerieDTO getSerieById(Long id){
        return convertSerieDTO(serieRepository.findSerieById(id));
    }

    public List<SerieDTO> getAllSeries(){
        return convertSerieDTOList(serieRepository.findAll());
    }

    public List<SerieDTO> getSeriesTop5(){
        return convertSerieDTOList(serieRepository.findTop5ByOrderByRating());
    }

    public List<SerieDTO> getSeriesByGenre(String g){
        List<Serie> serieList = serieRepository.findAll();
        List<Serie> filterList = new ArrayList<>();
        for (Serie serie : serieList){
            for (Genre genre : serie.getGenreList()){
                if (genre.getGenre() == GenreEnum.fromPortuguese(g)){
                    filterList.add(serie);
                }
            }
        }
        return convertSerieDTOList(filterList);
    }

    public List<SerieDTO> getSeriesByReleaseDateTest(){
        return convertSerieDTOList(serieRepository.findTop5ByOrderByReleased());
    }

    public List<SerieDTO> getSeriesByReleaseDate(){
        List<Serie> serieList = serieRepository.findAll();
        List<Serie> seriesByRelease = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusMonths(3);
        boolean b;
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

    public List<EpisodeDTO> convertEpisodeDTOList(List<Episode> episodeList){
        return episodeList.stream()
                .map(this::convertEpisodeDTO).toList();
    }

    public EpisodeDTO convertEpisodeDTO(Episode e){
        return new EpisodeDTO(
                e.getId(),
                e.getSeasonNumber(),
                e.getEpisodeNumber(),
                e.getTitle()
        );
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

}
