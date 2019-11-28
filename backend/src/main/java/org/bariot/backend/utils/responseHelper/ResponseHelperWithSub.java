package org.bariot.backend.utils.responseHelper;

import org.bariot.backend.service.core.CRUDService;
import org.bariot.backend.service.core.CRUDServiceSubItems;
import org.bariot.backend.utils.Identifiable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SuppressWarnings("unchecked")
public class ResponseHelperWithSub<Parent extends Identifiable, Child extends Identifiable> extends CRUDResponseHelper<Parent> {

    private CRUDServiceSubItems subService;

    public ResponseHelperWithSub(CRUDService<Parent> parentService, CRUDServiceSubItems<Parent, Child> subService) {
        super(parentService);
        this.subService = subService;
    }

    public ResponseEntity addToSubSet(Long parentID, Child item) {
        if (subService.addToSubSet(parentID, item))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Cannot add subItem");
    }

    public ResponseEntity getCountOfSub(Long parentID) {
        Integer result = subService.getCountOfSub(parentID);
        if (result != null)
            return ResponseEntity.ok(result);
        return ResponseEntity.badRequest().body("Cannot get count of subItems");
    }

    public ResponseEntity getAllSubs(Long parentID) {
        List<Child> all = subService.getAllSubs(parentID);
        if (all != null)
            return ResponseEntity.ok(all);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot get all subItems");
    }
}
