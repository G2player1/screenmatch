package Enos.projetoSpring.screenmatch.service;

import Enos.projetoSpring.screenmatch.dto.EmployeeDTO;
import Enos.projetoSpring.screenmatch.dto.GenreDTO;
import Enos.projetoSpring.screenmatch.dto.SerieDTO;
import Enos.projetoSpring.screenmatch.models.Title;
import Enos.projetoSpring.screenmatch.repository.ITitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleService {

    @Autowired
    private ITitleRepository repository;

    public List<SerieDTO> getAllSeries(){
        return convertSerieDTO(repository.findByTypeContainingIgnoreCase("series"));
    }

    public List<SerieDTO> getSeriesTop5(){
        return convertSerieDTO(repository.findTop5ByTypeOrderByRating("series"));
    }

    public List<SerieDTO> convertSerieDTO(List<Title> serieList){
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
}
