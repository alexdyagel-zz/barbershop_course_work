package by.dyagel.controller;

import by.dyagel.controller.messages.UserMessage;
import by.dyagel.model.entities.Order;
import by.dyagel.model.entities.Service;
import by.dyagel.model.entities.Specialist;
import by.dyagel.model.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class ManageOrdersController extends Controller {

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Integer> idColumn;

    @FXML
    private TableColumn<Order, Service> serviceColumn;

    @FXML
    private TableColumn<Order, Specialist> specialistColumn;

    @FXML
    private TableColumn<Order, Date> dateColumn;

    @FXML
    private TableColumn<Order, Time> timeColumn;

    @FXML
    private TableColumn<Order, User> clientColumn;

    @FXML
    private TableColumn<Order, String> nameColumn;

    @FXML
    private TableColumn<Order, String> EmailColumn;

    @FXML
    private TableColumn<Order, String> phoneNumberColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private Button statisticsButton;

    @FXML
    private TextField serviceTextField;

    @FXML
    private TextField specialistTextField;

    @FXML
    private TextField clientTextField;

    @FXML
    private Button searchButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Button statisticsSpecialistsButton;

    @FXML
    private Button backButton;

    private ArrayList<Order> listOfOrders = new ArrayList<>();

    private ObservableList<Order> ordersData;

    private ArrayList<TextField> listOfFields = new ArrayList<>();

    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        listOfFields.add(clientTextField);
        listOfFields.add(serviceTextField);
        listOfFields.add(specialistTextField);

        ordersData = FXCollections.observableArrayList();
        listOfOrders = initListWithDataFromServer(listOfOrders, UserMessage.GET_ORDERS);
        ordersData.addAll(listOfOrders);

        idColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory("service"));
        specialistColumn.setCellValueFactory(new PropertyValueFactory("specialist"));
        dateColumn.setCellValueFactory(new PropertyValueFactory("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory("time"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("FIO"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory("Email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
        orderTable.setItems(ordersData);
    }


    @FXML
    void onPressBackButton(ActionEvent event) {
        openNewScene(backButton, "../view/adminMenu.fxml", "Меню администратора");
    }

    @FXML
    void onPressDeleteButton(ActionEvent event) throws IOException, ClassNotFoundException {
        ObservableList<Order> selectedOrders = orderTable.getSelectionModel().getSelectedItems();
        ObservableList<Order> orders = orderTable.getItems();
        for (Order order : selectedOrders) {
            if (sendToServer(order, UserMessage.DELETE_ORDER)) {
                orders.remove(order);
            }
        }
        initialize();
    }

    @FXML
    void onPressSearchButton(ActionEvent event) {
        if (!isFieldEmpty(serviceTextField)) {
            String service = serviceTextField.getText().trim();
            FilteredList<Order> flist = orderTable.getItems().filtered((tr) -> tr.getService().getName().contains(service));
            orderTable.setItems(flist);
        }
        if (!isFieldEmpty(specialistTextField)) {
            String specialist = specialistTextField.getText().trim();
            FilteredList<Order> flist = orderTable.getItems().filtered((tr) ->
                    tr.getSeance().getSpecialist().getName().contains(specialist));
            orderTable.setItems(flist);
        }
        if (!isFieldEmpty(clientTextField)) {
            String client = clientTextField.getText().trim();
            FilteredList<Order> flist = orderTable.getItems().filtered((tr) ->
                    tr.getUser().getFIO().contains(client));
            orderTable.setItems(flist);
        }
    }

    @FXML
    void onPressStatisticsButton(ActionEvent event) {
        openNewScene(backButton, "../view/statistics.fxml", "Статистика услуг");
    }


    @FXML
    void onPressRefreshButton(ActionEvent event) throws IOException, ClassNotFoundException {
        initialize();
    }

    @FXML
    void onPressStatisticsSpecialistsButton(ActionEvent event) {
        openNewScene(backButton,"../view/statisticsSpecialists.fxml", "Статистика специалистов");
    }
}

