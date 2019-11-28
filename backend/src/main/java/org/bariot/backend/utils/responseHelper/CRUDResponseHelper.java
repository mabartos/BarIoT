package org.bariot.backend.utils.responseHelper;

import org.bariot.backend.service.core.CRUDService;
import org.bariot.backend.utils.Identifiable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CRUDResponseHelper<T extends Identifiable> {

    private CRUDService<T> service;

    public CRUDResponseHelper(CRUDService<T> service) {
        this.service = service;
    }

    public CRUDService<T> getCRUDService() {
        return service;
    }

    public ResponseEntity create(T model) {
        T entity = service.create(model);
        if (entity != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(entity);
        }
        return ResponseEntity.badRequest().body("Cannot create model!!");
    }

    public ResponseEntity deleteByID(long id) {
        if (service.deleteByID(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot delete model");
    }

    public ResponseEntity update(long id, T model) {
        T result = service.update(id, model);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot update model");
    }

    public ResponseEntity updateByProps(long id, String updatesJSON) {
        T result = service.updateByProps(id, updatesJSON);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body("Cannot update model");
    }

    public ResponseEntity getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    public ResponseEntity getByID(long id) {
        T result = service.getByID(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot get model by ID");
    }
}
