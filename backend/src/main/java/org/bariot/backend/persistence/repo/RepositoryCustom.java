package org.bariot.backend.persistence.repo;

public interface RepositoryCustom<U> {
    U getByName(String name);

}

