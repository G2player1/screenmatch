package Enos.projetoSpring.screenmatch.models;

public class Episode {
    private String title;
    private Integer episodeNumber;
    private Integer runtime;
    private String plot;

    public Episode(String title, Integer episodeNumber) {
        this.title = title;
        this.episodeNumber = episodeNumber;
    }

    public Episode(EpisodeDetailedData episodeDetailedData) {
        this.title = episodeDetailedData.title();
        this.episodeNumber = episodeDetailedData.episodeNumber();
        this.runtime = Integer.parseInt(episodeDetailedData.runtime().replaceAll("([^0-9]+)",""));
        this.plot = episodeDetailedData.episodePlot();
    }

    public String getPlot() {
        return plot;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Epsiode Title: " + title + '\n' +
                "Episode Number: " + episodeNumber + "\n" +
                "Runtime: " + runtime + "\n" +
                "Plot: \n" + plot + '\n';
    }
}
