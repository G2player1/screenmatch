package Enos.projetoSpring.screenmatch.models;

import Enos.projetoSpring.screenmatch.enums.GenreEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "genre")
    @Convert(converter = GenreConverter.class)
    @Enumerated(EnumType.STRING)
    private GenreEnum genre;
    @ManyToOne
    private Title title;

    public Genre(){}

    public Genre(GenreEnum genre){
        this.genre = genre;
    }

    protected void setTitle(Title title){this.title = title; }

    public GenreEnum getGenre() {
        return genre;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genre=" + genre +
                ", id=" + id +
                '}';
    }
}
