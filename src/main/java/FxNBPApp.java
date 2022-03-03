import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nbpApi.Rate;
import nbpApi.Table;
import repository.RateRepository;
import repository.RateRepositoryNBPCached;
import service.ServiceNBP;
import service.ServiceNBPApi;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FxNBPApp extends Application {
    private VBox root = new VBox(); // to jest kontener z pionowym układem
    private Scene scene = new Scene(root, 600,400); // 600 to szerokość, a 400 to wysokość
    private Label amountLabel = new Label("Kwota do wymiany");
    private TextField amount = new TextField();
    private ComboBox<Rate> sourceCode = new ComboBox<>();
    private ComboBox<Rate> targetCode = new ComboBox<>();
    private ComboBox<Table> table = new ComboBox<>();
    private Label result = new Label("0,0");
    private Button runExchange = new Button("Przelicz");
    private Button changeTable = new Button("Zmień tabelę");
    private RateRepository repository = new RateRepositoryNBPCached();
    private ServiceNBP serviceNBP = new ServiceNBPApi(repository);

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        builGUI(stage);
    }

    private double calc(double amount, Rate source, Rate target){
        return amount * source.getMid() / target.getMid();
    }

    private void calcExchangeResult(){
        double input = Double.parseDouble(amount.getText());
        result.setText(String.format("%8.2f", calc(input, sourceCode.getValue(), targetCode.getValue())));
//        try {
//            double output = serviceNBP.calc(input,
//                    sourceCode.getSelectionModel().getSelectedItem().getCode(),
//                    targetCode.getSelectionModel().getSelectedItem().getCode()
//
//            );
//            result.setText(String.format("%6.2f", output));
//        } catch (IOException e) {
//            result.setText("Błąd pobierania danych!");
//        } catch (InterruptedException e) {
//            result.setText("Przerwano połączenie z serwerem!");
//        }
    }

    private void changeCodes(){
        try {
            final List<Rate> rates = serviceNBP.findAll(table.getValue());
            sourceCode.getItems().clear();
            targetCode.getItems().clear();
            sourceCode.getItems().addAll(rates);
            targetCode.getItems().addAll(rates);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void builGUI(Stage stage){
        changeTable.setOnAction(event -> {
            changeCodes();
        });
        runExchange.setOnAction(event -> {
            calcExchangeResult();
        });
        root.setSpacing(10); // odległość między kontrolkami
        root.setPadding(new Insets(10)); // wszystkie marginesy ustawione na 10
        root.getChildren().addAll(
                table,
                changeTable,
                amountLabel,
                amount,
                sourceCode,
                targetCode,
                result,
                runExchange
        );
        table.getItems().addAll(Table.values());
        root.setAlignment(Pos.TOP_CENTER);
        try {
            sourceCode.getItems().addAll(serviceNBP.findAll(Table.TABLE_A, LocalDate.now()));
            targetCode.getItems().addAll(serviceNBP.findAll(Table.TABLE_A, LocalDate.now()));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.setTitle("Waluty z NBP");
        stage.show();
    }
}