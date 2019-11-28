package org.bariot.backend.service.core;

import org.bariot.backend.utils.Identifiable;

import java.util.List;

public interface GeneralLayeredService<T extends Identifiable> {
    List<T> getAllItems(Long... id);

    T getByID(Long... id);

    T create(T entity, Long... id);

    T update(T entity, Long... id);

    T updateItems(String updatesJSON, Long... id);

    boolean delete(Long... id);
}
