package hu.pinterbeci.server.service.implementation;

import hu.pinterbeci.server.enumeration.Status;
import hu.pinterbeci.server.model.Server;
import hu.pinterbeci.server.repo.ServerRepo;
import hu.pinterbeci.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

@RequiredArgsConstructor
//
@Service //azonosítja az osztályunkat, mint egy Service-t
@Transactional // CDI által kezelt bean-en lehetővé teszi a tranzakciókat
@Slf4j //lombok annotáció a log-olás miatt, legyen nyoma a server log-ban, mit művelnek a kliensen
public class ServerServiceImpl implements ServerService {

    //mivel a dekláráció után kérte, hogy 'Variable 'serverRepo' might not have been initialized'
    //így a lombok ebben is segít
    //ezen service repo fogja elvégezni a JPA műveleteket, és menedzselni az adatokat.
    private final ServerRepo serverRepo;

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by ID: {}", id);
        return serverRepo.findById(id).isPresent() ? serverRepo.findById(id).get() : null;
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server by ID: {}", server.getId());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by ID: {}", id);
        serverRepo.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        //internet protocolt leíró osztály
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(100000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    private String setServerImageUrl() {
        String[] imageName = {"server1.png,server2.png,server3.png,server4.png"};
        int imageIndex = new Random().nextInt(4);
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image/" + imageName[imageIndex]).toUriString();
    }
}
