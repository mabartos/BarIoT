package org.bariot.backend.service.core;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.UserModel;
import org.springframework.stereotype.Service;

public interface UserService extends CRUDServiceSubItems<UserModel, HomeModel> {
}
