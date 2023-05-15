package hu.pinterbeci.server.repo;

import hu.pinterbeci.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

//menedzselt osztály neve (JPA entitás), és ID-jának típusa
//JpaRepository --> Spring Boot adja számunkra, azzal a céllal, hogy adatokat tudjunk menedzselni az alkalmazásunk.4
//ezen oknál fogva már a CRUD műveletek metódusait szolgáltatja számunkra, amit már nem kell megírjunk
public interface ServerRepo extends JpaRepository<Server, Long> {

    //további metódusokat írunk meg, melyeket nem örökli az osztály (két select-et)
    //kvázi erre a JPA entitásra szabva
    Server findByIpAddress(String ipAddress);

    Server findByName(String name);

}
