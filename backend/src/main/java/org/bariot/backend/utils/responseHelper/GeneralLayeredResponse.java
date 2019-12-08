package org.bariot.backend.utils.responseHelper;

import org.bariot.backend.service.core.GeneralLayeredService;
import org.bariot.backend.service.core.UserService;
import org.bariot.backend.utils.Identifiable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SuppressWarnings("unchecked")
public class GeneralLayeredResponse<T extends Identifiable> {

    private GeneralLayeredService generalService;
    private UserService userService;

    public GeneralLayeredResponse(GeneralLayeredService generalService, UserService userService) {
        this.generalService = generalService;
        this.userService = userService;
    }

    public ResponseEntity getAllItems(Long... id) {
        List<T> list = generalService.getAllItems(id);
        if (list != null)
            return ResponseEntity.ok(list);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find all items");
    }

    public ResponseEntity getByID(Long... id) {
        T entity = (T) generalService.getByID(id);
        if (entity != null)
            return ResponseEntity.ok(entity);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find item by ID");
    }

    public ResponseEntity create(Identifiable entity, Long... id) {
        T created = (T) generalService.create(entity, id);
        if (created != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        return ResponseEntity.badRequest().body("Cannot create entity");
    }

    public ResponseEntity  createFromJSON(String JSON, Long... id) {
        T created = (T) generalService.createFromJSON(JSON, id);
        if (created != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        return ResponseEntity.badRequest().body("Cannot create entity");
    }

    public ResponseEntity update(Identifiable entity, Long... id) {
        T updated = (T) generalService.update(entity, id);
        if (updated != null)
            return ResponseEntity.ok(updated);
        return ResponseEntity.badRequest().body("Cannot update item");
    }

    public ResponseEntity updateItems(String updatesJSON, Long... id) {
        T entity = (T) generalService.updateItems(updatesJSON, id);
        if (entity != null)
            return ResponseEntity.ok(entity);
        return ResponseEntity.badRequest().body("Cannot update item");
    }

    public ResponseEntity delete(Long... id) {
        if (generalService.delete(id)) {
            userService.update(id[0], userService.getByID(id[0]));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot delete item");
    }
}
