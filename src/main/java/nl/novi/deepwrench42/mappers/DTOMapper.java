package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.entities.BaseEntity;
import java.util.List;

public interface DTOMapper<RESPONSE, REQUEST, T> {
    RESPONSE mapToDto(T model);

    List<RESPONSE> mapToDto(List<T> models);

    T mapToEntity(REQUEST genreModel);
}