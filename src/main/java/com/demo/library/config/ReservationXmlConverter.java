package com.demo.library.config;

import com.demo.library.dto.ReservationsWrapper;
import com.demo.library.entity.Reservation;

import java.util.List;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class ReservationXmlConverter {

    public static String convertListToXml(List<Reservation> reservations) throws Exception {
        JAXBContext context = JAXBContext.newInstance(ReservationsWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        ReservationsWrapper wrapper = new ReservationsWrapper();
        wrapper.setReservations(reservations);

        StringWriter writer = new StringWriter();
        marshaller.marshal(wrapper, writer);
        return writer.toString();
    }
}
