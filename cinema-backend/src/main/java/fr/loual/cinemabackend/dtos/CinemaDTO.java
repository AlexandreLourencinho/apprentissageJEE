package fr.loual.cinemabackend.dtos;

import lombok.Data;

import java.util.Collection;

@Data
public class CinemaDTO {

    private String id;
    private String name;
    private double longitude;
    private double latitude;
    private double altitude;
    private int numberOfRooms;
    private CityDTO city;
    private Collection<RoomDTO> rooms;
}
