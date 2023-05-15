package hu.pinterbeci.server.resource;

import hu.pinterbeci.server.enumeration.Status;
import hu.pinterbeci.server.model.Response;
import hu.pinterbeci.server.model.Server;
import hu.pinterbeci.server.service.implementation.ServerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;


@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {

    private final ServerServiceImpl serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() {

        Map<String, Collection<Server>> dataMap = new HashMap<>();
        dataMap.put("servers", serverService.list(5));
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(dataMap)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) {
        try {
            Server server = serverService.ping(ipAddress);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("server", server);
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .data(dataMap)
                            .message(server.getStatus().equals(Status.SERVER_UP) ? "Ping succes" : "Ping failed")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/save")
    //@RequestBody annotáció-val jelezzük, hogy a request body-ban lesz az információ, amit küldeni szeretnénk
    //@Valid - megnézi, vam e ráhelyezve validáció az entitásra, és van
    public ResponseEntity<Response> sabeServer(@RequestBody @Valid Server server) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("server", serverService.create(server));
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(dataMap)
                        .message("Server created!")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("server", serverService.get(id));
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(dataMap)
                        .message("Server retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("deleted", serverService.delete(id));
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(dataMap)
                        .message("Server deleted")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    //HTTP kérés esetén az annotációban az is megadható, hogy milyen formában készül el a válasz
    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get("d:/server_images/" + fileName));
    }
}
