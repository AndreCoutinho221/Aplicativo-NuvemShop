package com.andre.nuvemapp.repositorio;

import com.andre.nuvemapp.entidades.Loja;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LojaRepository extends CrudRepository<Loja, String> {

}
