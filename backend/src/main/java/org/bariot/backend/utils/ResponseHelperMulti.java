package org.bariot.backend.utils;

import org.bariot.backend.persistence.repo.HomesRepository;
import org.bariot.backend.persistence.repo.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @param <Model>      Parent
 * @param <ChildModel> This model
 */
@SuppressWarnings("unchecked")
public class ResponseHelperMulti<Model extends IBasicInfo, ChildModel extends IBasicInfo> {

    private Model model;

    private HomesRepository homesRepo;
    private UsersRepository usersRepo;

    public ResponseHelperMulti(UsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    public ResponseHelperMulti(UsersRepository usersRepo, HomesRepository homesRepo) {
        this.homesRepo = homesRepo;
        this.usersRepo = usersRepo;
    }

    public boolean isInited(Long... ids) {
        try {
            if (ids != null) {
                if (ids.length != 0) {
                    Model root = new UserPathHelper(usersRepo).getPath(ids);
                    if (root != null) {
                        this.model = root;
                        return true;
                    }
                }
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public ResponseEntity<List<ChildModel>> getAll() {
        List<ChildModel> list = model.getAllSubs();
        if (list != null && !list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<ChildModel> getItem(long id) {
        ChildModel child = (ChildModel) getSubByIdFromList(id, model.getAllSubs());
        if (child != null) {
            return ResponseEntity.ok(child);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<ChildModel> addExistingHomeToUser(long idHome) {
        try {
            if (this.homesRepo != null) {
                Optional optHome = homesRepo.findById(idHome);
                if (optHome.isPresent()) {
                    ChildModel child = (ChildModel) optHome.get();
                    model.getAllSubs().add(child);
                    return ResponseEntity.ok(child);
                }
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();

        }
    }

    public ResponseEntity<ChildModel> createItem(ChildModel child) {
        try {
            if (child != null) {
                model.getAllSubs().add(child);
                URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                        .buildAndExpand(child.getId())
                        .toUri();
                return ResponseEntity.created(uri).body(child);
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<ChildModel> deleteItem(ChildModel child) {
        if (child != null) {
            ChildModel fromList = (ChildModel) getSubByIdFromList(child.getId(), model.getAllSubs());
            if (fromList != null) {
                if (model.getAllSubs().remove(child))
                    return ResponseEntity.ok(child);
            }
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<ChildModel> deleteItemById(long id) {
        ChildModel fromList = (ChildModel) getSubByIdFromList(id, model.getAllSubs());
        if (fromList != null) {
            if (model.getAllSubs().remove(fromList))
                return ResponseEntity.ok(fromList);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<ChildModel> updateItem(long id, ChildModel item) {
        if (item != null) {
            ChildModel fromList = (ChildModel) getSubByIdFromList(id, model.getAllSubs());
            if (fromList != null) {
                fromList = item;
                return ResponseEntity.ok(item);
            }
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<ChildModel> updateItemProps(long id, Map<String, String> updates) {
        if (updates != null) {
            ChildModel fromList = (ChildModel) getSubByIdFromList(id, model.getAllSubs());
            if (fromList != null) {
                ChildModel updated = UpdateHelper.updateItems(fromList, updates);
                if (updated != null)
                    return ResponseEntity.ok(updated);
            }
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> removeAllSub() {
        List<ChildModel> entities = model.getAllSubs();
        if (entities != null) {
            entities.clear();
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    private ChildModel getSubByIdFromList(Long id, List<ChildModel> list) {
        if (list != null) {
            Optional opt = list.stream().filter(f -> f.getId().equals(id)).findFirst();
            if (opt.isPresent()) {
                return (ChildModel) opt.get();
            }
        }
        return null;
    }

}
