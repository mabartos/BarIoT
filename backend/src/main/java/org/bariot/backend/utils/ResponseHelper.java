package org.bariot.backend.utils;

import org.bariot.backend.service.core.CRUDService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Class helps to return ResponseEntity
 * @param <T> Model class
 * @param <T> Repository class
 */
@SuppressWarnings("unchecked")
public class ResponseHelper<T extends Identifiable> {

    private CRUDService<T> service;

    public ResponseHelper(CRUDService<T> service) {
        this.service = service;
    }

    /**
     * Returns all entities of T class
     */
    public ResponseEntity<List<T>> getAll() {
        List<T> list = service.getAll();
        if (list != null) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Returns list of parents' child
     * f.e. returns all Homes of User instance
     * Method called from child to parent !!
     *
     * @param id Parent id - f.e. User
     */
    public <Model extends IBasicInfo> ResponseEntity<List<Model>> getParentsSub(long id) {
        ResponseEntity<T> parent = getById(id);
        try {
            List<Model> list = parent.getBody().getAllSubs();
            if (list != null && !list.isEmpty()) {
                return ResponseEntity.ok(list);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Returns list of children from some service
     * Method called from anywhere !!
     *
     * @param subEntityRepo Generics Repo -
     */
    public <Repo extends JpaRepository, Model extends IBasicInfo> ResponseEntity<List<Model>>
    getAllSub(Repo subEntityRepo) {
        List<Model> list = subEntityRepo.getAll();
        if (list != null && !list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Adds child to parent
     * f.e. parent like UserModel and child like HomeModel (or HomeModel and RoomModel)
     * Method called from parent!!
     *
     * @param subEntityRepo Repository of child
     */
    public <ChildRepo extends JpaRepository, Model extends IBasicInfo> ResponseEntity<Model>
    addSubEntity(Long parentID, Long childId, ChildRepo subEntityRepo) {
        try {
            Optional optParent = service.getByID(parentID);
            Optional optChild = subEntityRepo.getByID(childId);
            if (optParent.isPresent() && optChild.isPresent()) {
                T parent = (T) optParent.get();
                Model child = (Model) optChild.get();
                parent.addToSubSet(child);
                child.addToSubSet(parent);
                return ResponseEntity.ok(child);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Returns Model by name
     */
    private T getByNameFromRepo(String name) {
        Optional opts = service.getAll()
                .stream().filter(f -> f.getName().equals(name))
                .findAny();
        if (opts.isPresent())
            return (T) opts.get();
        else
            return null;
    }

    /**
     * Returns Model by Id or Name
     * Rather use getById
     * Use only in case of unique name
     */
    public ResponseEntity<T> getByIdOrName(String idOrName) {
        T entity = null;

        try {
            long id = Long.parseLong(idOrName);
            Optional entityOpt = service.getByID(id);
            if (entityOpt.isPresent())
                return ResponseEntity.ok((T) entityOpt.get());
        } catch (NumberFormatException e) {
            entity = getByNameFromRepo(idOrName);
        }

        if (entity != null) {
            return ResponseEntity.ok(entity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Return model by ID
     */
    public ResponseEntity<T> getById(long id) {
        Optional entity = service.getByID(id);
        if (entity.isPresent()) {
            return ResponseEntity.ok((T) entity.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Returns Model by Name
     * Rather use getById
     * Use only in case of unique name
     */
    public ResponseEntity<T> getByName(String name) {
        T entity = getByNameFromRepo(name);
        if (entity != null) {
            return ResponseEntity.ok(entity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates Model
     */
    public ResponseEntity<T> create(T entity) {
        T created = service.save(entity);
        if (created != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .buildAndExpand(created.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(created);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add child to parent list
     *
     * @param idParent
     * @param idChild
     * @param helperChild
     * @param <ChildModel>
     * @return ChildModel added to parent list
     */
    public <ChildModel extends IBasicInfo> ResponseEntity<ChildModel> addExistingChild(Long idParent, Long idChild, ResponseHelper<ChildModel, ?> helperChild) {
        ChildModel child = helperChild.getById(idChild).getBody();
        if (child != null) {
            T parent = getById(idParent).getBody();
            if (parent != null) {
                parent.getAllSubs().add(child);
                return ResponseEntity.ok(child);
            }
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * REMOVES child from parent
     */
    public <ChildRepo extends JpaRepository, ChildModel extends IBasicInfo> ResponseEntity<ChildModel>
    removeChildFromParent(Long idParent, Long idChild, ChildRepo childRepo) {
        try {
            Optional parentOpt = service.getByID(idParent);
            Optional childOpt = childRepo.getByID(idChild);
            if (parentOpt.isPresent() && childOpt.isPresent()) {
                T parent = (T) parentOpt.get();
                ChildModel child = (ChildModel) childOpt.get();
                if (parent.getAllSubs().remove(child)) {
                    return ResponseEntity.ok(child);
                }
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Removes all Children from parent list
     *
     * @param helper
     * @param idParent
     * @param <ChildModel>
     */
    public <ChildModel extends IBasicInfo> ResponseEntity<Void> removeAllChildrenFromParent(Long idParent, ResponseHelper<ChildModel, ?> helper) {
        List<IBasicInfo> entities = helper.getParentsSub(idParent).getBody();
        if (entities != null) {
            entities.clear();
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes Model by Name
     * Rather use deleteById
     * Use only in case of unique name
     */
    public ResponseEntity<T> deleteByIdOrName(String idOrName) {
        T entity = null;
        try {
            long id = Long.parseLong(idOrName);
            Optional entityOpt = service.getByID(id);
            if (entityOpt.isPresent())
                entity = (T) entityOpt.get();
        } catch (NumberFormatException e) {
            entity = getByNameFromRepo(idOrName);
        }

        if (entity != null) {
            service.delete(entity);
            return ResponseEntity.ok(entity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes Model by ID
     */
    public ResponseEntity<T> deleteById(Long id) {
        Optional entityOpt = service.getByID(id);
        if (entityOpt.isPresent()) {
            T entity = (T) entityOpt.get();
            service.deleteById(id);
            return ResponseEntity.ok((T) entityOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Returns specific child of parent
     *
     * @param idParent  ID of parent
     * @param idChild   ID of Child
     * @param childRepo Child service - where sub is located
     */
    public <ChildRepo extends JpaRepository, ChildModel extends IBasicInfo> ResponseEntity<ChildModel>
    getSubByID(Long idParent, Long idChild, ChildRepo childRepo) {
        try {
            Optional parentOpt = service.getByID(idParent);
            Optional childOpt = childRepo.getByID(idChild);
            if (parentOpt.isPresent() && childOpt.isPresent()) {
                T parent = (T) parentOpt.get();
                ChildModel child = (ChildModel) childOpt.get();
                if (parent.getAllSubs().contains(child)) {
                    return ResponseEntity.ok(child);
                }
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public <ChildModel extends IBasicInfo> ResponseEntity<ChildModel> getSubByIdFromList(Long id, List<ChildModel> list) {
        if (list != null) {
            Optional opt = list.stream().filter(f -> f.getId().equals(id)).findFirst();
            if (opt.isPresent()) {
                return ResponseEntity.ok((ChildModel) opt.get());
            }
        }
        return ResponseEntity.notFound().build();
    }


    public ResponseEntity<T> update(Long id, T model) {
        Optional entityOpt = service.getByID(id);
        if (entityOpt.isPresent()) {
            model.setId(id);
            service.save(model);
            return ResponseEntity.ok(model);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<T> update(Long id, Map<String, String> updates) {
        Optional userOpt = service.getByID(id);
        if (userOpt.isPresent()) {
            T entity = (T) userOpt.get();
            entity = UpdateHelper.updateItems(entity, updates);
            if (entity != null) {
                service.save(entity);
                return ResponseEntity.ok(entity);
            }
        }
        return ResponseEntity.notFound().build();
    }
}

