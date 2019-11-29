package org.bariot.backend.service.core.impl;

import org.bariot.backend.service.core.CRUDServiceSubItems;
import org.bariot.backend.utils.IBasicInfo;
import org.bariot.backend.utils.Identifiable;
import org.bariot.backend.utils.UpdateHelper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class CRUDServiceSubItemsImpl<Parent extends IBasicInfo<Child>, Child extends Identifiable>
        extends CRUDServiceImpl<Parent> implements CRUDServiceSubItems<Parent, Child> {

    public CRUDServiceSubItemsImpl(JpaRepository<Parent, Long> repository, UpdateHelper<Parent> updateHelper) {
        super(repository, updateHelper);
    }

    @Override
    public boolean addToSubSet(Long idParent, Child item) {
        Optional optional = getRepository().findById(idParent);
        if (optional.isPresent() && item != null) {
            Parent parent = (Parent) optional.get();
            return parent.addToSubSet(item);
        }
        return false;
    }

    @Override
    public Integer getCountOfSub(Long idParent) {
        Optional optional = getRepository().findById(idParent);
        if (optional.isPresent()) {
            Parent parent = (Parent) optional.get();
            return parent.getCountOfSub();
        }
        return null;
    }

    @Override
    public List<Child> getAllSubs(Long idParent) {
        Optional optional = getRepository().findById(idParent);
        if (optional.isPresent()) {
            Parent parent = (Parent) optional.get();
            return parent.getAllSubs();
        }
        return null;
    }
}
