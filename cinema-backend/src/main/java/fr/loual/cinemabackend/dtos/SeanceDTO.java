package fr.loual.cinemabackend.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class SeanceDTO {

    private String id;
    private Date projectionDate;
    private double price;
    private Date beginDate;
    private MovieDTO movie;
    private RoomDTO room;


}
