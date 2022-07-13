package fr.loual.cinemabackend.dtos;

import lombok.Data;

@Data
public class RoomDTO {

    private String id;
    private String name;
    private int roomSize;
    private CinemaDTO cinema;
}
