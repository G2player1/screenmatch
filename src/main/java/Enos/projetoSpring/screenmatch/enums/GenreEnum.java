package Enos.projetoSpring.screenmatch.enums;

public enum GenreEnum {
    ACTION("ação","Action",1),
    CRIME("crime","Crime",2),
    COMEDY("comédia","Comedy",3),
    FANTASY("fantasia","Fantasy",4),
    ROMANCE("romance","Romance",5),
    DRAMA("drama","Drama",6),
    THRILLER("thriller","Thriller",7),
    ANIMATION("animação","Animation",8),
    FAMILY("familia","Family",9),
    HORROR("horror","Horror",10),
    ADVENTURE("aventura","Adventure",11);

    private final String genreScreenMatch;
    private final String genreOMDB;
    private final Integer id;

    GenreEnum(String genreScreenMatch ,String genreOMDB, Integer id){
        this.genreScreenMatch = genreScreenMatch;
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

    public static GenreEnum fromPortuguese(String value){
        for (GenreEnum genreEnum : GenreEnum.values()){
            if(genreEnum.genreScreenMatch.equalsIgnoreCase(value)){
                return genreEnum;
            }
        }
        throw new IllegalArgumentException("None genre detected");
    }

    public static GenreEnum fromId(Integer value){
        for (GenreEnum genreEnum : GenreEnum.values()){
            if(genreEnum.id.equals(value)){
                return genreEnum;
            }
        }
        throw new IllegalArgumentException("None genre detected");
    }
}
