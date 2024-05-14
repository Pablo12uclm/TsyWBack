package edu.uclm.esi.juegos.http2;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200") // Cambia el puerto si es necesario
public class MyController {

    @GetMapping("/data")
    public String getData() {
        return "Some data from backend";
    }

    @PostMapping("/data")
    public String postData(@RequestBody String data) {
        // Procesa los datos recibidos
        return "Data received: " + data;
    }
}
