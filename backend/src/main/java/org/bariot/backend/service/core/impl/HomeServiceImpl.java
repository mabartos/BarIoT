package org.bariot.backend.service.core.impl;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.service.core.HomeService;
import org.bariot.backend.utils.UpdateHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl extends CRUDServiceSubItemsImpl<HomeModel, UserModel> implements HomeService {
    public HomeServiceImpl(JpaRepository<HomeModel, Long> repository, UpdateHelper<HomeModel> updateHelper) {
        super(repository,updateHelper);
    }
}
