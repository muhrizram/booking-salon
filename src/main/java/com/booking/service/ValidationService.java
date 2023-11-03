package com.booking.service;

import java.util.regex.Pattern;

import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;

import java.util.List;
import java.util.regex.Matcher;

public class ValidationService {
    // Buatlah function sesuai dengan kebutuhan
    public static boolean isID(String text) {
        String regex = "^[A-Za-z]{3}-\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        boolean result = matcher.matches();
        if(!result){
            PrintService.printSlashLine();
            System.out.println("Input tidak sesuai, contoh input: abc-001");
            PrintService.printSlashLine();
        }
        return result;
    }

    public static boolean isNumber(String number) {
        String regex = "^(?! )(\\d+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        boolean result = matcher.matches();
        if (!result) {
            PrintService.printSlashLine();
            System.out.println("Input hanya menerima angka");
            PrintService.printSlashLine();
        }
        return result;
    }

    public static boolean isCustomer(String customerID, List<Person> persons) {
        boolean result = DataService.getAllCustomer(persons)
                .stream()
                .anyMatch(customer -> customerID.equalsIgnoreCase(customer.getId()));
    
        if (!result) {
            PrintService.printSlashLine();
            System.out.println("CustomerID tidak terdaftar");
            PrintService.printSlashLine();
        }
    
        return result;
    }

    public static boolean isEmployee(String employeeID, List<Person> persons) {
        boolean result = DataService.getAllEmployee(persons).stream()
            .anyMatch(employee -> employeeID.equalsIgnoreCase(employee.getId()));
        
        if (!result) {
            PrintService.printSlashLine();
            System.out.println("EmployeeID tidak terdaftar");
            PrintService.printSlashLine();
        }
        
        return result;
    }

    public static boolean isService(String serviceID, List<Service> services) {
        return services.stream()
                .anyMatch(service -> serviceID.equalsIgnoreCase(service.getServiceId()));
    }

    public static boolean isContinue(String letter) {
        String regex = "^(?! )([YyNn])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(letter);
        boolean result = matcher.matches();
        if (!result) {
            PrintService.printSlashLine();
            System.out.println("Input tidak valid");
            PrintService.printSlashLine();
        }
        return result;
    }

    public static boolean hasReservation(String text, List<Reservation> reservations) {
        boolean result = reservations.stream()
                .anyMatch(data -> text.equalsIgnoreCase(data.getReservationId()));
        return result;
    }

    public static boolean isNullOrSpace(String text){
        String regex = "^\\s*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        boolean result = matcher.matches();
        return result;
    }

    public static boolean isCancelOrFinish(String text){
        String regex = "^(finish|cancel)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        boolean result = matcher.matches();
        if(!result){
            PrintService.printSlashLine();
            System.out.println("Input hanya menerima finish/cancel");
            PrintService.printSlashLine();
        }
        return result;
    }
}
