package Enos.projetoSpring.screenmatch.controllers;

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
    public List<Title> getSeries(){
        return repository.findByTypeContainingIgnoreCase("series");
    }

}
