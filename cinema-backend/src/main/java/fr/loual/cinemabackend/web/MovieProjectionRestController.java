package fr.loual.cinemabackend.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping("/movie-projection")
@AllArgsConstructor
public class MovieProjectionRestController {

}
