package com.andre.nuvemapp.repository;

import com.andre.nuvemapp.model.Loja;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LojaRepository extends CrudRepository<Loja, String> {

}
