package com.booking.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private String reservationId;
    private Customer customer;
    private Employee employee;
    private List<Service> services;
    private double reservationPrice;
    private String workstage;
    // workStage (In Process, Finish, Canceled)

    public Reservation(String reservationId, Customer customer, Employee employee, List<Service> services,
            String workstage) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.employee = employee;
        this.services = services;
        this.reservationPrice = calculateReservationPrice(services);
        this.workstage = workstage;
    };

    private double calculateReservationPrice(List<Service> services) {
        double result = 0;
        for (Service data : services) {
            result += data.getPrice();
        }
        double discount = getDiscount(customer.getMember().getMembershipName());
        result -= result * discount;
        return result;
    }

    private double getDiscount(String customerMember) {
        double result = 0;
        if (customerMember.equalsIgnoreCase("silver")) {
            result = 0.05;
        } else if (customerMember.equalsIgnoreCase("gold")) {
            result = 0.1;
        }
        return result;
    }
}
