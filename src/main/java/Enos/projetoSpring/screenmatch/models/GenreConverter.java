package Enos.projetoSpring.screenmatch.models;

import Enos.projetoSpring.screenmatch.enums.GenreEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenreConverter implements AttributeConverter<GenreEnum,String> {

    @Override
    public String convertToDatabaseColumn(GenreEnum genreEnum) {
        return genreEnum.name();
    }

    @Override
    public GenreEnum convertToEntityAttribute(String s) {
        return GenreEnum.valueOf(s);
    }
}
