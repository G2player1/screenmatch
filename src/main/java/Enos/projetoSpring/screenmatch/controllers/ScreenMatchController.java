package Enos.projetoSpring.screenmatch.controllers;

import Enos.projetoSpring.screenmatch.models.omdbData.EpisodeDetailedData;
import Enos.projetoSpring.screenmatch.models.omdbData.TitleData;
import Enos.projetoSpring.screenmatch.service.ConsumeAPI;
import Enos.projetoSpring.screenmatch.service.ConvertData;

public class ScreenMatchController {

    private final ConsumeAPI consumeAPI = new ConsumeAPI();
    private final ConvertData convertData = new ConvertData();

    public TitleData getTitleDataWeb(String address){
        String jsonResponse = consumeAPI.getData(address);
        return convertData.getData(jsonResponse, TitleData.class);
    }

    public EpisodeDetailedData getEpisodeDetailedData(String address){
        String jsonResponse = consumeAPI.getData(address);
        return convertData.getData(jsonResponse, EpisodeDetailedData.class);
    }

}
