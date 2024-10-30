package Enos.projetoSpring.screenmatch.controllers;

import Enos.projetoSpring.screenmatch.models.TitleData;
import Enos.projetoSpring.screenmatch.service.ConsumeAPI;
import Enos.projetoSpring.screenmatch.service.ConvertData;

public class ScreenMatchController {

    private final ConsumeAPI consumeAPI = new ConsumeAPI();

    public TitleData getTitleWebData(String address){
        String jsonResponse = consumeAPI.getData(address);
        ConvertData convertData = new ConvertData();
        return convertData.getData(jsonResponse, TitleData.class);
    }
}
