package Enos.projetoSpring.screenmatch.enums;

public enum Genre {
    ACTION("Action"),
    CRIME("Crime"),
    COMEDY("Comedy"),
    FANTASY("Fantasy"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    THRILLER("Thriller"),
    ANIMATION("Animation"),
    FAMILY("Family"),
    HORROR("Horror"),
    ADVENTURE("Adventure");

    private final String genreOMDB;

    Genre(String genreOMDB){
        this.genreOMDB = genreOMDB;
    }

    public static Genre fromString(String value){
        for (Genre genre : Genre.values()){
            if(genre.genreOMDB.equalsIgnoreCase(value)){
                return genre;
            }
        }
        throw new IllegalArgumentException("None genre detected");
    }
}
