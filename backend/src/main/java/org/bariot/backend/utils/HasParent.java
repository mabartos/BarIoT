package org.bariot.backend.utils;

public interface HasParent<T> {
    T getParent();

    void setParent(T parent);
}
