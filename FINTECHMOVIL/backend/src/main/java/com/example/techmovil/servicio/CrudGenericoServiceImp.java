package com.example.techmovil.servicio;

import com.example.techmovil.excepciones.CustomResponse;
import com.example.techmovil.modelo.Activable;
import com.example.techmovil.repositorio.CrudGenericoRepository;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public abstract class CrudGenericoServiceImp<T, K> implements CrudGenericoService<T, K> {

    private static final String ID_LABEL = "ID ";
    private static final String SUFIJO_NO_ENCONTRADO = " no fue encontrado en la base de datos.";
    private static final String SUFIJO_NO_EXISTE = " no existe en la base de datos.";

    protected abstract CrudGenericoRepository<T, K> getRepo();

    @Override
    public T save(T t) { return getRepo().save(t); }

    @Override
    public T update(T t, K id) {
        if (!getRepo().existsById(id)) {
            throw new EntityNotFoundException(ID_LABEL + id + SUFIJO_NO_EXISTE);
        }
        return getRepo().save(t);
    }

    @Override
    public List<T> findAll() { return getRepo().findAll(); }

    @Override
    public T findById(K id) {
        return getRepo().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ID_LABEL + id + SUFIJO_NO_ENCONTRADO));
    }

    @Override
    public CustomResponse delete(K id) {
        T entity = findById(id);
        if (entity instanceof Activable activable) {
            activable.setActivo(false);
            getRepo().save(entity);
        } else {
            getRepo().deleteById(id);
        }
        return CustomResponse.builder()
                .statusCode(200)
                .datetime(LocalDateTime.now())
                .message("Exito")
                .details("El registro con ID " + id + " fue eliminado logicamente correctamente.")
                .build();
    }
}
