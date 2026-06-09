package com.example.techmovil.servicio;

import com.example.techmovil.excepciones.CustomResponse;
import java.util.List;

public interface CrudGenericoService<T, K> {
    T save(T t);
    T update(T t, K id);
    List<T> findAll();
    T findById(K id);
    CustomResponse delete(K id);
}
