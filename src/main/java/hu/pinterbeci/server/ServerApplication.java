package hu.pinterbeci.server;

import hu.pinterbeci.server.enumeration.Status;
import hu.pinterbeci.server.model.Server;
import hu.pinterbeci.server.repo.ServerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//Spring Boot application belépési pontja
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepo serverRepo) {
        return args -> {
            serverRepo.save(new Server(null, "192.168.1.160", "Ubuntu", "16 GB", "Personal PC", "http://localhost:8080/server/image/server1.png", Status.SERVER_UP));
            serverRepo.save(new Server(null, "192.168.1.161", "Fedora Linux", "32 GB", "Dell Tower", "http://localhost:8080/server/image/server2.png", Status.SERVER_DOWN));
            serverRepo.save(new Server(null, "192.168.1.162", "MS 2008", "64 GB", "Web Server", "http://localhost:8080/server/image/server3.png", Status.SERVER_UP));
            serverRepo.save(new Server(null, "192.168.1.163", "Red Hat Enterprise Linux", "8 GB", "Application Server", "http://localhost:8080/server/image/server4.png", Status.SERVER_UP));

        };
    }

}
