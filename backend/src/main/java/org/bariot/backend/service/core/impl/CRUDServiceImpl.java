package org.bariot.backend.service.core.impl;

import org.bariot.backend.service.core.CRUDService;
import org.bariot.backend.utils.Identifiable;
import org.bariot.backend.utils.UpdateHelper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class CRUDServiceImpl<T extends Identifiable> implements CRUDService<T> {

    private JpaRepository<T, Long> repo;

    private UpdateHelper<T> updateHelper;

    public CRUDServiceImpl(JpaRepository<T, Long> repository, UpdateHelper<T> updateHelper) {
        this.updateHelper = updateHelper;
        this.repo = repository;
    }

    public JpaRepository<T, Long> getRepository() {
        return repo;
    }

    @Override
    public T createFromJSON(T model, String json) {
        return createFromJSON(model, json, null);
    }

    @Override
    public T createFromJSON(T model, String json, Long parentID) {
        try {
            if (json != null && model != null) {
                T entity = updateHelper.updateItems(model, json, parentID);
                return repo.save(entity);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public T create(T model) {
        try {
            if (model != null) {
                return repo.save(model);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteByID(long id) {
        try {
            if (getByID(id) == null)
                return false;
            repo.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public T update(long id, T model) {
        try {
            if (model != null) {
                model.setID(id);
                return repo.save(model);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public T updateByProps(long id, String updatesJSON) {
        try {
            Optional opt = repo.findById(id);
            if (opt.isPresent() && updatesJSON != null) {
                T entity = (T) opt.get();
                T updated = updateHelper.updateItems(entity, updatesJSON);
                if (updated != null) {
                    updated.setID(id);
                    return repo.save(updated);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<T> getAll() {
        try {
            return repo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public T getByID(long id) {
        try {
            Optional modelOpt = repo.findById(id);
            if (modelOpt.isPresent()) {
                return (T) modelOpt.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
