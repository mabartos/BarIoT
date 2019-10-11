package org.bariot.backend.utils;

import org.bariot.backend.controller.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SuppressWarnings("unchecked")
public class ResponseHelperMulti<Model extends IbasicInfo> {

    private Model model;

    @Autowired
    private UserResource userResource;

    public ResponseHelperMulti(Long... ids) {
        this.model = new UserPathHelper().getPath(ids);
    }

    public ResponseEntity<List<Model>> getAll() {
        List<Model> list = model.getAllSubs();
        if (list != null && !list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
