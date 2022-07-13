package fr.loual.cinemabackend.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class MovieDTO {

    private String id;
    private String title;
    private int duration;
    private String maker;
    private String description;
    private String picture;
    private Date releaseate;
    private CategoryDTO category;

}
