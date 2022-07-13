package fr.loual.cinemabackend.dtos;

import lombok.Data;

@Data
public class PlaceDTO {

    private String id;
    private int number;
    private double longitude;
    private double latitude;
    private double altitude;
    private RoomDTO room;
    private TicketDTO ticket;
}
