package org.bariot.backend.service.auth;

import org.bariot.backend.persistence.model.UserModel;

public interface SecurityUserService {
    void autoLogin(String username, String password);

    String getBasicToken(String username, String password);

    String getAuthJson(String username, String password, UserModel user);
}
