package org.bariot.backend.utils;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Class helps to return ResponseEntity
 * @param <U> Model class
 * @param <T> Repository class
 */
@SuppressWarnings("unchecked")
public class ResponseHelper<U extends IbasicInfo, T extends JpaRepository<U, Long>> {

    private T repository;

    public ResponseHelper(T repository) {
        this.repository = repository;
    }

    /**
     * Returns all entities of T class
     */
    public ResponseEntity<List<U>> getAll() {
        List<U> list = repository.findAll();
        if (list != null && !list.isEmpty()) {
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
    public <Model extends IbasicInfo> ResponseEntity<List<Model>> getParentsSub(long id) {
        ResponseEntity<U> parent = getById(id);
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
     * Returns list of children from some repository
     * Method called from anywhere !!
     *
     * @param subEntityRepo Generics Repo -
     */
    public <Repo extends JpaRepository, Model extends IbasicInfo> ResponseEntity<List<Model>>
    getAllSub(Repo subEntityRepo) {
        List<Model> list = subEntityRepo.findAll();
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
    public <ChildRepo extends JpaRepository, Model extends IbasicInfo> ResponseEntity<Model>
    addSubEntity(Long parentID, Long childId, ChildRepo subEntityRepo) {
        try {
            Optional optParent = repository.findById(parentID);
            Optional optChild = subEntityRepo.findById(childId);
            if (optParent.isPresent() && optChild.isPresent()) {
                U parent = (U) optParent.get();
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
    private U getByNameFromRepo(String name) {
        Optional opts = repository.findAll()
                .stream().filter(f -> f.getName().equals(name))
                .findAny();
        if (opts.isPresent())
            return (U) opts.get();
        else
            return null;
    }

    /**
     * Returns Model by Id or Name
     * Rather use getById
     * Use only in case of unique name
     */
    public ResponseEntity<U> getByIdOrName(String idOrName) {
        U entity = null;

        try {
            long id = Long.parseLong(idOrName);
            Optional entityOpt = repository.findById(id);
            if (entityOpt.isPresent())
                return ResponseEntity.ok((U) entityOpt.get());
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
    public ResponseEntity<U> getById(long id) {
        Optional entity = repository.findById(id);
        if (entity.isPresent()) {
            return ResponseEntity.ok((U) entity.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Returns Model by Name
     * Rather use getById
     * Use only in case of unique name
     */
    public ResponseEntity<U> getByName(String name) {
        U entity = getByNameFromRepo(name);
        if (entity != null) {
            return ResponseEntity.ok(entity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates Model
     */
    public ResponseEntity<U> create(U entity) {
        U created = repository.save(entity);
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
    public <ChildModel extends IbasicInfo> ResponseEntity<ChildModel> addExistingChild(Long idParent, Long idChild, ResponseHelper<ChildModel, ?> helperChild) {
        ChildModel child = helperChild.getById(idChild).getBody();
        if (child != null) {
            U parent = getById(idParent).getBody();
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
    public <ChildRepo extends JpaRepository, ChildModel extends IbasicInfo> ResponseEntity<ChildModel>
    removeChildFromParent(Long idParent, Long idChild, ChildRepo childRepo) {
        try {
            Optional parentOpt = repository.findById(idParent);
            Optional childOpt = childRepo.findById(idChild);
            if (parentOpt.isPresent() && childOpt.isPresent()) {
                U parent = (U) parentOpt.get();
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
     * @param idParent
     * @param helper
     * @param <ChildModel>
     */
    public <ChildModel extends IbasicInfo> ResponseEntity<Void> removeAllChildrenFromParent(Long idParent, ResponseHelper<ChildModel, ?> helper) {
        List<IbasicInfo> entities = helper.getParentsSub(idParent).getBody();
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
    public ResponseEntity<U> deleteByIdOrName(String idOrName) {
        U entity = null;
        try {
            long id = Long.parseLong(idOrName);
            Optional entityOpt = repository.findById(id);
            if (entityOpt.isPresent())
                entity = (U) entityOpt.get();
        } catch (NumberFormatException e) {
            entity = getByNameFromRepo(idOrName);
        }

        if (entity != null) {
            repository.delete(entity);
            return ResponseEntity.ok(entity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes Model by ID
     */
    public ResponseEntity<U> deleteById(Long id) {
        Optional entityOpt = repository.findById(id);
        if (entityOpt.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok((U) entityOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Returns specific child of parent
     *
     * @param idParent  ID of parent
     * @param idChild   ID of Child
     * @param childRepo Child repository - where sub is located
     */
    public <ChildRepo extends JpaRepository, ChildModel extends IbasicInfo> ResponseEntity<ChildModel>
    getSubByID(Long idParent, Long idChild, ChildRepo childRepo) {
        try {
            Optional parentOpt = repository.findById(idParent);
            Optional childOpt = childRepo.findById(idChild);
            if (parentOpt.isPresent() && childOpt.isPresent()) {
                U parent = (U) parentOpt.get();
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

    public <ChildModel extends IbasicInfo> ResponseEntity<ChildModel> getSubByIdFromList(Long id, List<ChildModel> list) {
        if (list != null) {
            Optional opt = list.stream().filter(f -> f.getId().equals(id)).findFirst();
            if (opt.isPresent()) {
                return ResponseEntity.ok((ChildModel) opt.get());
            }
        }
        return ResponseEntity.notFound().build();
    }


    public ResponseEntity<U> update(Long id, U model) {
        Optional entityOpt = repository.findById(id);
        if (entityOpt.isPresent()) {
            model.setId(id);
            repository.save(model);
            return ResponseEntity.ok(model);
        }
        return ResponseEntity.notFound().build();
    }
}
