package org.bariot.backend.service.core;

import org.bariot.backend.utils.Identifiable;

import java.util.List;

public interface CRUDServiceSubItems<Parent extends Identifiable, Child extends Identifiable> extends CRUDService<Parent> {

    boolean addToSubSet(Long idParent, Child item);

    Integer getCountOfSub(Long idParent);

    List<Child> getAllSubs(Long idParent);
}
