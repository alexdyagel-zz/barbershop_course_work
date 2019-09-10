package by.dyagel.controller;

import by.dyagel.controller.messages.UserMessage;
import by.dyagel.model.entities.Order;
import by.dyagel.model.entities.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;

public class StatisticsController extends Controller {

    @FXML
    private Label text;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button backButton;

    private ArrayList<Order> listOfOrders = new ArrayList<>();

    private ArrayList<Service> listOfServices = new ArrayList<>();


    @FXML
    private void initialize() throws IOException {
        listOfOrders = initListWithDataFromServer(listOfOrders, UserMessage.GET_ORDERS);
        listOfServices = initListWithDataFromServer(listOfServices, UserMessage.GET_SERVICES);

        PieChart pieChartService = new PieChart();
        ObservableList<PieChart.Data> pieChartDataService = FXCollections.observableArrayList();
        for (Service service : listOfServices) {
            pieChartDataService.add(new PieChart.Data(service.getName(), getPercentOfOrders(service)));
        }
        pieChartService.setData(pieChartDataService);
        pieChartService.setStartAngle(30);

        final Label caption = new Label("");
        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font: 24 arial;");

        initPieChart(pieChartService, caption);

        gridPane.add(pieChartService, 0, 0);
        gridPane.add(caption, 0, 0);
    }


    double getPercentOfOrders(Service service) {
        int meetingNumber = 0;
        for (Order order : listOfOrders) {
            if (order.getService().toString().equals(service.toString())) {
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

