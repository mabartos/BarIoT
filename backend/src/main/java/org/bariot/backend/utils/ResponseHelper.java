package org.bariot.backend.utils;

import org.bariot.backend.persistence.repo.RepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class ResponseHelper<U extends hasID, T extends JpaRepository<U, Long> & RepositoryCustom<U>> {

    private T repository;

    public ResponseHelper(T repository) {
        this.repository = repository;
    }

    public ResponseEntity<U> getByIdOrName(String idOrName) {
        U entity = null;

        try {
            long id = Long.parseLong(idOrName);
            Optional entityOpt = repository.findById(id);
            if (entityOpt.isPresent())
                return ResponseEntity.ok((U) entityOpt.get());
        } catch (NumberFormatException e) {
            entity = repository.getByName(idOrName);
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
        U entity = repository.getByName(name);
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

    public ResponseEntity<U> deleteByIdOrName(String idOrName) {
        U entity = null;
        try {
            long id = Long.parseLong(idOrName);
            Optional entityOpt = repository.findById(id);
            if (entityOpt.isPresent())
                entity = (U) entityOpt.get();
        } catch (NumberFormatException e) {
            entity = repository.getByName(idOrName);
        }

        if (entity != null) {
            repository.delete(entity);
            return ResponseEntity.ok(entity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
