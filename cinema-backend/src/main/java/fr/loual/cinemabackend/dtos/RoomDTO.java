package fr.loual.cinemabackend.dtos;

import lombok.Data;

import java.util.Collection;

@Data
public class RoomDTO {

    private String id;
    private String name;
    private int roomSize;
    private CinemaDTO cinema;
    private Collection<SeanceDTO> seance;
    private Collection<PlaceDTO> place;

}
