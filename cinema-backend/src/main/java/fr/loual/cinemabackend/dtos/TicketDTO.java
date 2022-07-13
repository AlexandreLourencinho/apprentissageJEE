package fr.loual.cinemabackend.dtos;

import lombok.Data;

@Data
public class TicketDTO {

    private String id;
    private String clientName;
    private double price;
    private int payementCode;
    private boolean reserved;
    private SeanceDTO seance;
    private PlaceDTO place;

}
