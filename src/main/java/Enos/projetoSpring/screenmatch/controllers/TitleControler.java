package Enos.projetoSpring.screenmatch.controllers;

import Enos.projetoSpring.screenmatch.dto.EmployeeDTO;
import Enos.projetoSpring.screenmatch.dto.GenreDTO;
import Enos.projetoSpring.screenmatch.dto.SerieDTO;
import Enos.projetoSpring.screenmatch.models.Serie;
import Enos.projetoSpring.screenmatch.models.Title;
import Enos.projetoSpring.screenmatch.repository.ITitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TitleControler {

    @Autowired
    private ITitleRepository repository;

    @GetMapping("/series")
    public List<SerieDTO> getSeries(){
        return   repository.findByTypeContainingIgnoreCase("series")
                .stream()
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
