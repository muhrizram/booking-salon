package com.booking.service;

import java.util.List;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;

public class PrintService {
    public static void printMenu(String title, String[] menuArr) {
        int num = 1;
        if (!ValidationService.isNullOrSpace(title)) {
            System.out.println(title);
        }
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);
            num++;
        }
    }

    public String printServices(List<Service> serviceList) {
        String result = "";
        // Bisa disesuaikan kembali
        for (Service service : serviceList) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public void showRecentReservation(List<Reservation> reservationList) {
        int num = 1;
        System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        PrintService.printEqualLine();
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("In process")) {
                System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                        num, reservation.getReservationId(), reservation.getCustomer().getName(),
                        printServices(reservation.getServices()), reservation.getReservationPrice(),
                        reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
            }
        }
        PrintService.printEqualLine();
    }

    public void showAllService(List<Service> serviceList) {
        int num = 1;
        System.out.printf("| %-4s | %-7s | %-19s | %-10s |\n", "No", "ID", "Nama", "Harga");
        PrintService.printEqualLine();
        for (Service data : serviceList) {
            System.out.printf("| %-4s | %-7s | %-19s | %-10s |\n", num, data.getServiceId(), data.getServiceName(),
                    data.getPrice());
            num++;
        }
        PrintService.printEqualLine();
    }

    public void showAllCustomer(List<Person> persons) {
        int num = 1;
        System.out.printf("| %-4s | %-7s | %-11s | %-15s | %-11s | %-12s |\n", "No", "ID", "Nama", "Alamat",
                "Membership",
                "Uang");
        System.out
                .println("+========================================================================================+");
        List<Customer> customers = DataService.getAllCustomer(persons);
        for (Customer data : customers) {
            System.out.printf("| %-4s | %-7s | %-11s | %-15s | %-11s | %-12s |\n", num, data.getId(),
                    data.getName(),
                    data.getAddress(), data.getMember().getMembershipName(),
                    data.getWallet());

            num++;
        }
        PrintService.printEqualLine();
    }

    public void showAllEmployee(List<Person> persons) {
        int num = 1;
        System.out.printf("| %-4s | %-7s | %-11s | %-15s | %-10s |\n", "No", "ID", "Nama", "Alamat", "Pengalaman");
        System.out
                .println("+========================================================================================+");
        List<Employee> employees = DataService.getAllEmployee(persons);
        for (Employee data : employees) {
            System.out.printf("| %-4s | %-7s | %-11s | %-15s | %-10s |\n", num, data.getId(), data.getName(),
                    data.getAddress(), data.getExperience());

            num++;
        }
    }

    public void showHistoryReservation(List<Reservation> reservations) {
        int num = 1;
        int profitTotal = 0;
        System.out.printf("| %-4s | %-6s | %-11s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Total Biaya", "Workstage");
        PrintService.printEqualLine();
        for (Reservation data : reservations) {
            System.out.printf("| %-4s | %-6s | %-11s | %-15s | %-15s | %-10s |\n",
                    num, data.getReservationId(), data.getCustomer().getName(), printServices(data.getServices()),
                    data.getReservationPrice(), data.getWorkstage());
            num++;
            if (data.getWorkstage().equalsIgnoreCase("Finish")) {
                profitTotal += data.getReservationPrice();
            }
        }
        PrintService.printEqualLine();
        System.out.printf("| %-34s | %-25s |\n",
                "Total Keuntungan", "Rp." + profitTotal);
    }

    public static void printSlashLine() {
        System.out.println("//////////////////////");
    }

    public static void printEqualLine() {
        System.out
                .println("+========================================================================================+");
    }
}
