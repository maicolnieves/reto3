/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentcloud.cloud.app.services;

import com.rentcloud.cloud.app.entities.Client;
import com.rentcloud.cloud.app.repositories.ClientRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gamer
 */
@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;
    
    /**
     * GET
     * @return Lista de client
     */
    public List<Client> getAll(){
        return repository.getAll();
    }
    
    
    /**
     * GET/{id}
     * @param clientId
     * @return 
     */
    public Optional<Client> getClient(int clientId){
        return repository.getClient(clientId);
    }
    
    
    /**
     * POST
     * @param client
     * @return 
     */
    public Client save(Client client){
        //Consultar si se envia el ID
        if (client.getIdClient()==null){
            return repository.save(client);
        }else{
            //Consultar si existe el registro
            Optional <Client> existClient = repository.getClient(client.getIdClient());
            if (existClient.isPresent()){
                return client;
            }else{
                return repository.save(client);
            }
        }
    }
    
    /**
     * UPDATE
     * @param client
     * @return 
     */
    public Client update (Client client){
        if (client.getIdClient()!=null){
            Optional<Client> existClient = repository.getClient(client.getIdClient());
            if(existClient.isPresent()){
                if (client.getEmail()!=null){
                    existClient.get().setEmail(client.getEmail());
                }
                if (client.getPassword()!=null){
                    existClient.get().setPassword(client.getPassword());
                }
                if (client.getName()!=null){
                    existClient.get().setName(client.getName());
                }
                if (client.getMessages()!=null){
                    existClient.get().setMessages(client.getMessages());
                }
                if (client.getReservations()!=null){
                    existClient.get().setReservations(client.getReservations());
                }
                return repository.save(existClient.get());
            }else{
                return client;
            }
        }else{
            return client;
        }
    }
    
    public boolean delete(int clientId){
        Boolean respuesta = getClient(clientId).map (client -> {
            repository.delete(client);
            return true;
        }).orElse(false);
        return respuesta;
    }
}
