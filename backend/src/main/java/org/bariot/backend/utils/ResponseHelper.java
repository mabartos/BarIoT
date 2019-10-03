package org.bariot.backend.utils;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * @param <U> Model class
 * @param <T> Repository class
 */
@SuppressWarnings("unchecked")
public class ResponseHelper<U extends IbasicInfo, T extends JpaRepository<U, Long>> {

    private T repository;

    public ResponseHelper(T repository) {
        this.repository = repository;
    }

    public ResponseEntity<List<U>> getAll() {
        List<U> list = repository.findAll();
        if (list != null && !list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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

    public <Repo extends JpaRepository, Model extends IbasicInfo> ResponseEntity<List<Model>> getAllSub(Repo subEntityRepo) {
        List<Model> list = subEntityRepo.findAll();
        if (list != null && !list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public <Repo extends JpaRepository, Model extends IbasicInfo> ResponseEntity<Model> addSubEntity
            (Long parentID, Long childId, Repo subEntityRepo) {
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

    private U getByNameFromRepo(String name) {
        Optional opts = repository.findAll().stream().filter(f -> f.getName().equals(name)).findAny();
        if (opts.isPresent())
            return (U) opts.get();
        else
            return null;
    }

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

    public ResponseEntity<U> getById(long id) {
        Optional entity = repository.findById(id);
        if (entity.isPresent()) {
            return ResponseEntity.ok((U) entity.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<U> getByName(String name) {
        U entity = getByNameFromRepo(name);
        if (entity != null) {
            return ResponseEntity.ok(entity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<U> create(U entity) {
        U created = repository.save(entity);
        if (created != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(entity.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(entity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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

    public ResponseEntity<U> deleteById(Long id) {
        Optional entityOpt = repository.findById(id);
        if (entityOpt.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok((U) entityOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
