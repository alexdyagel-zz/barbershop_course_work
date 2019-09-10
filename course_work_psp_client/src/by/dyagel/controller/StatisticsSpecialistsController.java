package by.dyagel.controller;

import by.dyagel.controller.messages.UserMessage;
import by.dyagel.model.entities.Order;
import by.dyagel.model.entities.Specialist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;

public class StatisticsSpecialistsController extends Controller {

    @FXML
    private Label text;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button backButton;

    private ArrayList<Order> listOfOrders = new ArrayList<>();

    private ArrayList<Specialist> listOfSpecialists = new ArrayList<>();

    @FXML
    private void initialize() throws IOException {
        listOfOrders = initListWithDataFromServer(listOfOrders, UserMessage.GET_ORDERS);
        listOfSpecialists = initListWithDataFromServer(listOfSpecialists, UserMessage.GET_SPECIALISTS);


        PieChart pieChartSpecialists = new PieChart();
        ObservableList<PieChart.Data> pieChartDataSpecialists = FXCollections.observableArrayList();
        for (Specialist specialist : listOfSpecialists) {
            pieChartDataSpecialists.add(new PieChart.Data(specialist.toString(), getPercentOfOrders(specialist)));
        }
        pieChartSpecialists.setData(pieChartDataSpecialists);
        pieChartSpecialists.setStartAngle(30);

        final Label caption = new Label("");
        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font: 24 arial;");

        initPieChart(pieChartSpecialists,caption);
        gridPane.add(pieChartSpecialists, 0, 0);
        gridPane.add(caption, 0, 0);
    }


    double getPercentOfOrders(Specialist specialist) {
        int meetingNumber = 0;
        for (Order order : listOfOrders) {
            if (order.getSeance().getSpecialist().toString().equals(specialist.toString())) {
                meetingNumber += 1;
            }
        }
        return (meetingNumber / (float) listOfOrders.size()) * 100.0;
    }


    @FXML
    void onPressBackButton(ActionEvent event) {
        openNewScene(backButton, "../view/manageOrders.fxml", "Записи");
    }

}
