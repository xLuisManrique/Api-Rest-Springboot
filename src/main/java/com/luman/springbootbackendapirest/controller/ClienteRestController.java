package com.luman.springbootbackendapirest.controller;

import com.luman.springbootbackendapirest.models.entity.Cliente;
import com.luman.springbootbackendapirest.models.services.iClienteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins= "*")
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    @Autowired
    private iClienteServices clienteServices;
    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteServices.findAll();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();
        try {
            cliente = clienteServices.finById(id);
        } catch (DataAccessException e){
            response.put("mensaje", "Error en la consulta");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(cliente == null){
            response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> create(@RequestBody Cliente cliente){
        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();
        try{
            clienteNew = clienteServices.save(cliente);
        } catch (DataAccessException e){
            response.put("mensaje", "Error, no se puede crear el cliente");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ha sido creado");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id){
        Cliente clienteActual = clienteServices.finById(id);
        Cliente clienteUpdate = null;
        Map<String, Object> response = new HashMap<>();
        if(clienteActual == null){
            response.put("mensaje", "Error: No se puede editar, el ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
        clienteActual.setApellido(cliente.getApellido());
        clienteActual.setNombre(cliente.getNombre());
        clienteActual.setEmail(cliente.getEmail());
        clienteActual.setPhone(cliente.getPhone());
        clienteActual.setCreateAt(cliente.getCreateAt());
        clienteUpdate = clienteServices.save(clienteActual);
        } catch (DataAccessException e){
            response.put("mensaje", "Error, no se puede actualizar");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        response.put("mensaje", "El cliente ha sido actualizado");
        response.put("cliente", clienteUpdate);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try{
            clienteServices.delete(id);
        } catch (DataAccessException e){
            response.put("mensaje", "Error, no se puede eliminar");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ha sido eliminado");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
