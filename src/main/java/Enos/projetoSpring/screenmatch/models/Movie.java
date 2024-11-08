package Enos.projetoSpring.screenmatch.models;

import Enos.projetoSpring.screenmatch.models.omdbData.TitleData;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie extends Title{

    public Movie(){super();}

    public Movie(TitleData titleData) {
        super(titleData);
    }
}
