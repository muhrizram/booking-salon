package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;

public class MenuService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    private static List<Reservation> reservationList = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);

    public static void mainMenu() {
        String[] mainMenuArr = { "Show Data", "Create Reservation", "Complete/cancel reservation", "Exit" };
        String[] subMenuArr = { "Recent Reservation", "Show Customer", "Show Available Employee",
                "Show Reservation History", "Back to main menu" };
        PrintService print = new PrintService();

        String optionMainMenu;
        String optionSubMenu;

        boolean isNumber = true;
        boolean backToMainMenu = false;
        do {
            PrintService.printMenu("Main Menu", mainMenuArr);
            optionMainMenu = input.nextLine();
            isNumber = ValidationService.isNumber(optionMainMenu);
            if (isNumber) {
                int optionMainMenuNumber = Integer.valueOf(optionMainMenu);
                switch (optionMainMenuNumber) {
                    case 1:
                        do {
                            backToMainMenu = false;
                            PrintService.printMenu("Show Data", subMenuArr);
                            optionSubMenu = input.nextLine();
                            isNumber = ValidationService.isNumber(optionSubMenu);
                            if (isNumber) {
                                int optionSubMenuNumber = Integer.parseInt(optionSubMenu);
                                switch (optionSubMenuNumber) {
                                    case 1:
                                        print.showRecentReservation(reservationList);
                                        break;
                                    case 2:
                                        print.showAllCustomer(personList);
                                        break;
                                    case 3:
                                        print.showAllEmployee(personList);
                                        break;
                                    case 4:
                                        print.showHistoryReservation(reservationList);
                                        break;
                                    case 0:
                                        backToMainMenu = true;
                                        break;
                                    default:
                                        PrintService.printSlashLine();
                                        System.out.println("Input tidak sesuai");
                                        PrintService.printSlashLine();
                                }
                            }
                        } while (!backToMainMenu);
                        break;
                    case 2:
                        ReservationService.createReservation(print, reservationList, personList, serviceList);
                        break;
                    case 3:
                        ReservationService.editReservationWorkstage(print, reservationList);
                        break;
                    case 0:
                        System.exit(0);
                        break;
                    default:
                        PrintService.printSlashLine();
                        System.out.println("Input tidak sesuai");
                        PrintService.printSlashLine();
                }
            }
        } while (true);

    }
}
