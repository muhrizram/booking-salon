package com.booking.service;

import java.util.List;
import java.util.stream.Collectors;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;

public class DataService {
    public static List<Customer> getAllCustomer(List<Person> persons) {
        return persons.stream().filter(person -> person instanceof Customer).map(person -> (Customer) person)
                .collect(Collectors.toList());
    }

    public static List<Employee> getAllEmployee(List<Person> persons) {
        return persons.stream().filter(person -> person instanceof Employee).map(person -> (Employee) person)
                .collect(Collectors.toList());
    }

    public static Service getService(String serviceID, List<Service> services) {
        return services.stream()
                .filter(service -> serviceID.equalsIgnoreCase(service.getServiceId()))
                .findFirst()
                .orElse(null);
    }

    public static Person getPerson(String personID, List<Person> persons) {
        return persons.stream().filter(person -> personID.equalsIgnoreCase(person.getId())).findFirst().orElse(null);
    }

    public static Reservation getReservation(String reservationID, List<Reservation> reservations) {
        return reservations.stream()
                .filter(reservation -> reservationID.equalsIgnoreCase(reservation.getReservationId())).findFirst()
                .orElse(null);
    }

    public static void editReservation(String workstage, Reservation reservation) {
        reservation.setWorkstage(workstage);
    }
}
