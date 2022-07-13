package fr.loual.cinemabackend.dtos;

import lombok.Data;

@Data
public class CityDTO {

    private String id;
    private String name;
    private double longitude;
    private double latitude;
    private double altitude;

}
