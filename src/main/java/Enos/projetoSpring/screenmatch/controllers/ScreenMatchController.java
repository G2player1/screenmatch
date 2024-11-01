package Enos.projetoSpring.screenmatch.controllers;

import Enos.projetoSpring.screenmatch.models.EpisodeDetailedData;
import Enos.projetoSpring.screenmatch.models.TitleData;
import Enos.projetoSpring.screenmatch.service.ConsumeAPI;
import Enos.projetoSpring.screenmatch.service.ConvertData;

public class ScreenMatchController {

    private final ConsumeAPI consumeAPI = new ConsumeAPI();
    private final ConvertData convertData = new ConvertData();

    public TitleData getTitleWebData(String address){
        String jsonResponse = consumeAPI.getData(address);
        return convertData.getData(jsonResponse, TitleData.class);
    }

    public EpisodeDetailedData getEpisodeDetailedData(String address){
        String jsonResponse = consumeAPI.getData(address);
        return convertData.getData(jsonResponse, EpisodeDetailedData.class);
    }

}
