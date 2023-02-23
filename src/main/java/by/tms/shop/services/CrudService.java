package by.tms.shop.services;

import java.util.List;

public interface CrudService<T> {

    T create(T value);
    List<T> findAll();
    T findById(Long id);
    void deleteById(Long id);
    T update(T value);
}
