package com.demo.library.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.demo.library.entity.Reservation;

@XmlRootElement(name = "reservations")
public class ReservationsWrapper {

    private List<Reservation> reservations;

    @XmlElement(name = "reservation")
    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
