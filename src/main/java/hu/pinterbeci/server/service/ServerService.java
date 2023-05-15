package hu.pinterbeci.server.service;

import hu.pinterbeci.server.model.Server;

import java.io.IOException;
import java.util.Collection;

//az alkalmazás funkciói kerülnek ide, CRUD műveletek
public interface ServerService {

    Server create(Server server);

    //listázó metódus, melynek átadva egy 'limit' számot kellő mennyiségű JPA entitást listáz ki számunkra
    Collection<Server> list(int limit);

    Server get(Long id);

    Server update(Server server);

    Boolean delete(Long id);

    Server ping(String ipAddress) throws IOException;
}
