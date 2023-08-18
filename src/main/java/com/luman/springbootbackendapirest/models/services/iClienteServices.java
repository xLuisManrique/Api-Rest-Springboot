package com.luman.springbootbackendapirest.models.services;

import com.luman.springbootbackendapirest.models.entity.Cliente;

import java.util.List;

public interface iClienteServices {
    public List<Cliente> findAll();
    public Cliente finById(Long id);
    public Cliente save(Cliente cliente);
    public void delete(Long id);
}
