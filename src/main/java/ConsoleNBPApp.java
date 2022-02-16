import controller.ConsoleController;
import controller.Menu;
import controller.MenuItem;
import nbpApi.Rate;
import nbpApi.Table;
import repository.RateRepository;
import repository.RateRepositoryNBPApi;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ConsoleNBPApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final RateRepository rates = new RateRepositoryNBPApi();

    private static void printTable(List<Rate> list, Table table) {
        for (Rate rate : list) {
            switch (table) {
                case TABLE_A, TABLE_B -> System.out.printf("%-45s %5s %15.4f%n", rate.getCurrency(), rate.getCode(), rate.getMid());
                case TABLE_C -> System.out.printf("%-45s %5s %15.4f%n", rate.getCurrency(), rate.getCode(), rate.getBid());
            }
        }
    }

    private static void handleOptionTable(Table table) {
        try {
            printTable(rates.findByTableAndDate(table, LocalDate.now()), table);
        } catch (Exception e) {
            System.out.println("Błąd połączenia, nie można odczytać danych z sieci!");
            System.out.println("Bład : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Menu menu = Menu.builder()
                .items(
                        List.of(
                                MenuItem.builder()
                                        .action(() -> handleOptionTable(Table.TABLE_A))
                                        .label("Pobierz tabelę A")
                                        .build(),
                                MenuItem.builder()
                                        .action(() -> handleOptionTable(Table.TABLE_B))
                                        .label("Pobierz tabelę B")
                                        .build(),
                                MenuItem.builder()
                                        .action(() -> handleOptionTable(Table.TABLE_C))
                                        .label("Pobierz tabelę C")
                                        .build(),
                                MenuItem.builder()
                                        .label("Wyjście")
                                        .action(() -> {
                                            System.exit(0);
                                        })
                                        .build()
                        )
                ).build();
        ConsoleController controller = new ConsoleController(scanner, menu);
        controller.process();
    }
}
