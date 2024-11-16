package Enos.projetoSpring.screenmatch.enums;

public enum GenreEnum {
    ACTION("Action",1),
    CRIME("Crime",2),
    COMEDY("Comedy",3),
    FANTASY("Fantasy",4),
    ROMANCE("Romance",5),
    DRAMA("Drama",6),
    THRILLER("Thriller",7),
    ANIMATION("Animation",8),
    FAMILY("Family",9),
    HORROR("Horror",10),
    ADVENTURE("Adventure",11);

    private final String genreOMDB;
    private final Integer id;

    GenreEnum(String genreOMDB, Integer id){
        this.genreOMDB = genreOMDB;
        this.id = id;
    }

    public static GenreEnum fromString(String value){
        for (GenreEnum genreEnum : GenreEnum.values()){
            if(genreEnum.genreOMDB.equalsIgnoreCase(value)){
                return genreEnum;
            }
        }
        throw new IllegalArgumentException("None genre detected");
    }
}
