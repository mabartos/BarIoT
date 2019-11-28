package org.bariot.backend.service.core;

import org.bariot.backend.utils.Identifiable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CRUDService<T extends Identifiable> {
    T create(T model);

    boolean deleteByID(long id);

    T update(long id, T model);

    T updateByProps(long id, String updatesJSON);

    List<T> getAll();

    T getByID(long id);
}
