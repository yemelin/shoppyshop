package com.example.shoppyshop.service;

import com.example.shoppyshop.config.NullAwareBeanUtilsBean;
import com.example.shoppyshop.exceptions.InternalErrorException;
import com.example.shoppyshop.exceptions.NotFoundException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.InvocationTargetException;


public abstract class AbstractCrudService<E, R extends CrudRepository<E, Long>> {
    
    protected final R repo;
    protected final NullAwareBeanUtilsBean utilsBean;

    public AbstractCrudService(R repo, NullAwareBeanUtilsBean utilsBean) {
        this.repo = repo;
        this.utilsBean = utilsBean;
    }

    public E findById(long id) {
        return repo.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Iterable<E> findAll() {
        return repo.findAll();
    }

    public E save(E category) {
        return repo.save(category);
    }

    public E update(Long id, E category) {
        return repo.save(updatedEntity(id, category, false));
    }

    public E partialUpdate(Long id, E category) {
        return repo.save(updatedEntity(id, category, true));
    }

    protected E updatedEntity(Long id, E category, boolean isPartial) {
        BeanUtilsBean utilsBean = isPartial ? this.utilsBean : new BeanUtilsBean();
        E entity = findById(id);
        try {
            utilsBean.copyProperties(entity, category);
        }
        catch( InvocationTargetException | IllegalAccessException e){
            throw new InternalErrorException();
        }
        return entity;
    }

}
