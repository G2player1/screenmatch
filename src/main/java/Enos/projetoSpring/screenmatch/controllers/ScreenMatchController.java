package Enos.projetoSpring.screenmatch.controllers;

import Enos.projetoSpring.screenmatch.service.ConsumeAPI;
import Enos.projetoSpring.screenmatch.service.ConvertData;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScreenMatchController {

    private final ConsumeAPI consumeAPI = new ConsumeAPI();
    private final ConvertData convertData = new ConvertData();

    public <T> T getWebData(String address,Class<T> tclass){
        String jsonResponse = consumeAPI.getData(address);
        return convertData.getData(jsonResponse,tclass);
    }

}
