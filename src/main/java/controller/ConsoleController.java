package controller;

import java.util.Scanner;

public class ConsoleController {
    private final Scanner scanner;
    private final Menu menu;

    public ConsoleController(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    public void process() {
        while (true) {
            menu.print();
            if (scanner.hasNextInt()) {
                final int option = scanner.nextInt(); // to zmiera tylko cyfrę
                scanner.nextLine(); // w skanerze jest liczba i enter, więc nextLine zbiera też entera
                if (menu.isValidOption(option)) {
                    menu.run(option);
                } else {
                    System.err.println("Brak takiej opcji");
                }
            } else {
                System.err.println("Wpisz liczbę oznaczającą numer opcji!");
                scanner.nextLine(); // usunięcie niepoprawnych danych
            }
        }
    }
}