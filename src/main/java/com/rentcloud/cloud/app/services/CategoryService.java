/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentcloud.cloud.app.services;

import com.rentcloud.cloud.app.entities.Category;
import com.rentcloud.cloud.app.repositories.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gamer
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;
    
    /**
     * GET
     * @return Lista de category
     */
    public List<Category> getAll(){
        return repository.getAll();
    }
    
    
    /**
     * GET/{id}
     * @param categoryId
     * @return 
     */
    public Optional<Category> getCategory(int categoryId){
        return repository.getCategory(categoryId);
    }
    
    
    /**
     * POST
     * @param category
     * @return 
     */
    public Category save(Category category){
        //Consultar si se envia el ID
        if (category.getId()==null){
            return repository.save(category);
        }else{
            //Consultar si existe el registro
            Optional <Category> existCategory = repository.getCategory(category.getId());
            if (existCategory.isPresent()){
                return category;
            }else{
                return repository.save(category);
            }
        }
    }
    
    /**
     * UPDATE
     * @param category
     * @return 
     */
    public Category update (Category category){
        if (category.getId()!=null){
            Optional<Category> existCategory = repository.getCategory(category.getId());
            if(existCategory.isPresent()){
                if (category.getName()!=null){
                    existCategory.get().setName(category.getName());
                }
                if (category.getDescription()!=null){
                    existCategory.get().setDescription(category.getDescription());
                }
                if (category.getClouds()!=null){
                    existCategory.get().setClouds(category.getClouds());
                }
                return repository.save(existCategory.get());
            }else{
                return category;
            }
        }else{
            return category;
        }
    }
    
    public boolean delete(int categoryId){
        Boolean respuesta = getCategory(categoryId).map (category -> {
            repository.delete(category);
            return true;
        }).orElse(false);
        return respuesta;
    }
}
