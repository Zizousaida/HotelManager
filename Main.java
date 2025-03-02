package hotelmanager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Reservation {
    private String clientName;
    private String arrivalDate;
    private String departureDate;
    private int roomNumber;

    public Reservation(String clientName, String arrivalDate, String departureDate, int roomNumber) {
        this.clientName = clientName;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.roomNumber = roomNumber;
    }

    public String getClientName() {
        return clientName;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "clientName='" + clientName + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", roomNumber=" + roomNumber +
                '}';
    }
}

class HotelManager {
    private List<Reservation> reservations;

    public HotelManager() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void removeReservation(String clientName) {
        reservations.removeIf(reservation -> reservation.getClientName().equals(clientName));
    }

    public void displayReservations() {
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    public void saveReservationsToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(reservations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadReservationsFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            reservations = (List<Reservation>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        HotelManager manager = new HotelManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Ajouter une réservation");
            System.out.println("2. Supprimer une réservation");
            System.out.println("3. Afficher toutes les réservations");
            System.out.println("4. Sauvegarder les réservations");
            System.out.println("5. Charger les réservations");
            System.out.println("6. Quitter");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.println("Nom du client:");
                    String name = scanner.nextLine();
                    System.out.println("Date d'arrivée:");
                    String arrival = scanner.nextLine();
                    System.out.println("Date de départ:");
                    String departure = scanner.nextLine();
                    System.out.println("Numéro de chambre:");
                    int room = scanner.nextInt();
                    manager.addReservation(new Reservation(name, arrival, departure, room));
                    break;
                case 2:
                    System.out.println("Nom du client:");
                    String clientName = scanner.nextLine();
                    manager.removeReservation(clientName);
                    break;
                case 3:
                    manager.displayReservations();
                    break;
                case 4:
                    System.out.println("Nom du fichier:");
                    String saveFile = scanner.nextLine();
                    manager.saveReservationsToFile(saveFile);
                    break;
                case 5:
                    System.out.println("Nom du fichier:");
                    String loadFile = scanner.nextLine();
                    manager.loadReservationsFromFile(loadFile);
                    break;
                case 6:
                    scanner.close();
                    return;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }
}