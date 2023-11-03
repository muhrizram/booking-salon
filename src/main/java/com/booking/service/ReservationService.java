package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;

public class ReservationService {
    public static void createReservation(PrintService print, List<Reservation> reservations, List<Person> persons,
            List<Service> services) {
        Scanner input = new Scanner(System.in);

        String[] menus = { "Back To Main Menu" };
        boolean hasReservationID = false;
        boolean isID = true;
        boolean isNullOrSpace = false;
        int initNumber = 1;
        String reservationID = "";
        do {
            PrintService.printEqualLine();
            PrintService.printMenu("", menus);
            PrintService.printEqualLine();
            System.out.println("Silahkan masukkan Reservation ID:");
            System.out.println("Contoh valid input: res-01 (Kosong = Autogenerate)");
            reservationID = input.nextLine();
            if (reservationID.equalsIgnoreCase("0")) {
                MenuService.mainMenu();
            }
            isNullOrSpace = ValidationService.isNullOrSpace(reservationID);

            if (isNullOrSpace) {
                do {
                    reservationID = "Res-" + String.format("%02d", initNumber);
                    hasReservationID = ValidationService.hasReservation(reservationID, reservations);
                    initNumber++;
                } while (hasReservationID);
                isID = true;
            } else {
                isID = ValidationService.isID(reservationID);
                hasReservationID = ValidationService.hasReservation(reservationID, reservations);
                if (hasReservationID) {
                    PrintService.printSlashLine();
                    System.out.println("ReservationID sudah terdaftar");
                    PrintService.printSlashLine();
                }
            }

        } while (hasReservationID || !isID);

        print.showAllCustomer(persons);
        boolean isCustomer = true;
        String customerID = "";
        do {
            PrintService.printEqualLine();
            PrintService.printMenu("", menus);
            PrintService.printEqualLine();
            System.out.println("Silahkan masukkan Customer ID:");
            customerID = input.nextLine();
            if (customerID.equalsIgnoreCase("0")) {
                MenuService.mainMenu();
            }
            isCustomer = ValidationService.isCustomer(customerID, persons);
        } while (!isCustomer);
        Customer customer = (Customer) DataService.getPerson(customerID, persons);

        print.showAllEmployee(persons);
        boolean isEmployee = true;
        String employeeID = "";
        do {
            PrintService.printEqualLine();
            PrintService.printMenu("", menus);
            PrintService.printEqualLine();
            System.out.println("Silahkan masukkan Employee ID:");
            employeeID = input.nextLine();
            if (employeeID.equalsIgnoreCase("0")) {
                MenuService.mainMenu();
            }
            isEmployee = ValidationService.isEmployee(employeeID, persons);
        } while (!isEmployee);
        Employee employee = (Employee) DataService.getPerson(employeeID, persons);

        print.showAllService(services);
        boolean isService = true;
        boolean isServiceOnList = false;
        boolean isAddNew = false;
        String serviceID = "";
        String continueYes = "";
        List<Service> listOfReserveService = new ArrayList<>();
        int maxContinue = services.size();
        int loop = 0;
        do {
            PrintService.printEqualLine();
            PrintService.printMenu("", menus);
            PrintService.printEqualLine();
            System.out.println("Silahkan masukkan Service ID:");
            serviceID = input.nextLine();
            if (serviceID.equalsIgnoreCase("0")) {
                MenuService.mainMenu();
            }
            isService = ValidationService.isService(serviceID, services);
            if (!isService) {
                PrintService.printSlashLine();
                System.out.println("Service ID tidak terdaftar");
                PrintService.printSlashLine();
            }

            isServiceOnList = ValidationService.isService(serviceID, listOfReserveService);
            if (isServiceOnList) {
                PrintService.printSlashLine();
                System.out.println("Service sudah dipilih");
                PrintService.printSlashLine();
            }

            if (isService && !isServiceOnList) {
                listOfReserveService.add(DataService.getService(serviceID, services));
                loop++;
                if (loop < maxContinue) {
                    do {
                        System.out.println("Ingin service yang lain? (y/n)");
                        continueYes = input.nextLine();
                        isAddNew = ValidationService.isContinue(continueYes);

                    } while (!isAddNew && loop < maxContinue);
                }
            }
        } while (!isService || isServiceOnList || (continueYes.equalsIgnoreCase("y") && loop < maxContinue));

        Reservation reservation = new Reservation(reservationID, customer, employee, listOfReserveService,
                "In Process");
        reservations.add(reservation);
        PrintService.printEqualLine();
        System.out.println("Booking berhasil!");
        PrintService.printEqualLine();
        System.out.println("Total Biaya Booking: Rp." + reservation.getReservationPrice());
    }

    public static void editReservationWorkstage(PrintService print, List<Reservation> reservations) {
        Scanner input = new Scanner(System.in);

        String[] menus = { "Back To Main Menu" };
        String reservationID = "";
        boolean isReservationID = true;
        do {
            PrintService.printEqualLine();
            PrintService.printMenu("", menus);
            PrintService.printEqualLine();
            System.out.println("Silahkan masukkan Reservation ID");
            reservationID = input.nextLine();
            if (reservationID.equalsIgnoreCase("0")) {
                MenuService.mainMenu();
            }
            isReservationID = ValidationService.hasReservation(reservationID, reservations);
            if (!isReservationID) {
                PrintService.printSlashLine();
                System.out.println("ReservationID tidak terdaftar");
                PrintService.printSlashLine();
            }
        } while (!isReservationID);

        Reservation reservation = DataService.getReservation(reservationID, reservations);
        print.showAllService(reservation.getServices());

        boolean isCancelOrFinish = true;
        String workstage = "";
        do {
            PrintService.printEqualLine();
            PrintService.printMenu("", menus);
            PrintService.printEqualLine();
            System.out.println("Selesaikan reservasi: (finish/cancel)");
            workstage = input.nextLine().toLowerCase();
            if (workstage.equalsIgnoreCase("0")) {
                MenuService.mainMenu();
            }
            isCancelOrFinish = ValidationService.isCancelOrFinish(workstage);
        } while (!isCancelOrFinish);

        DataService.editReservation(workstage, reservation);

        PrintService.printEqualLine();
        System.out.println("Reservasi dengan ID " + reservationID + " sudah " + workstage + ".");
        PrintService.printEqualLine();
    }
}
