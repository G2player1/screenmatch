package Enos.projetoSpring.screenmatch.controllers;

import Enos.projetoSpring.screenmatch.dto.SerieDTO;
import Enos.projetoSpring.screenmatch.service.ConsumeAPI;
import Enos.projetoSpring.screenmatch.service.ConvertData;
import Enos.projetoSpring.screenmatch.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScreenMatchController {

    @Autowired
    private  TitleService titleService;

    private final  ConsumeAPI consumeAPI = new ConsumeAPI();
    private final  ConvertData convertData = new ConvertData();

    public <T> T getWebData(String address,Class<T> tclass){
        String jsonResponse = consumeAPI.getData(address);
        return convertData.getData(jsonResponse,tclass);
    }

    @GetMapping("/start")
    public String startApp(){
        return """
                <h1>Screen Match</h1>
                <h3>app started</h3>
                <p>The screen match app is automatically re-compiling</p>
                <p>the live running is working</p>
                """;
    }

    @GetMapping("/series")
    public List<SerieDTO> getSeries(){
        return titleService.getAllSeries();
    }

    @GetMapping("/series/top5")
    public  List<SerieDTO> getSeriesTop5(){
        return titleService.getSeriesTop5();
    }


}
