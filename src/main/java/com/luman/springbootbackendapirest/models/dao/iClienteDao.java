package com.luman.springbootbackendapirest.models.dao;

import com.luman.springbootbackendapirest.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface iClienteDao extends CrudRepository<Cliente, Long>{
}
