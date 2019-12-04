package org.bariot.backend.utils;

import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.persistence.repo.UsersRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("unchecked")
public class IsUnique {

    public static boolean isUsernameUnique(JpaRepository repository, String username) {
        if (username != null) {
            List<UserModel> userList = repository.findAll();
            if (userList != null) {
                return userList.stream().anyMatch(f -> f.getUsername().equals(username));
            }
        }
        return false;
    }

    public static <T extends Identifiable> boolean itemAlreadyInList(List<T> list, T object) {
        return list.stream().anyMatch(f -> f.getID() == object.getID() && f.equals(object));
    }

    public static boolean emailExists(UsersRepository repository, String email) {
        if (repository != null && email != null) {
            return repository.findAll().stream().anyMatch(f -> f.getEmail().equals(email));
        }
        return true;
    }
}
