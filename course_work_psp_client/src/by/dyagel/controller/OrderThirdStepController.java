package by.dyagel.controller;

import by.dyagel.Keeper;
import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.controller.messages.UserMessage;
import by.dyagel.model.entities.Order;
import by.dyagel.model.entities.Seance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class OrderThirdStepController extends Controller {

    @FXML
    private Text text;

    @FXML
    private Button nextButton;

    @FXML
    private ComboBox<Seance> timeComboBox;

    @FXML
    private Button backButton;

    private ArrayList<Seance> listOfAvailableSeancesAtChosenDate = new ArrayList<>();

    private ObservableList<Seance> seancesData;

    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        seancesData = FXCollections.observableArrayList();
        initListOfAvailableSeancesAtChosenDate();
        seancesData.addAll(listOfAvailableSeancesAtChosenDate);
        timeComboBox.setItems(seancesData);
    }

    private void initListOfAvailableSeancesAtChosenDate() {
        long searchedDateInMillis = getDatePart(Keeper.date);
        for (Seance seance : Keeper.listOfSeances) {
            long dateInMillis = getDatePart(seance.getDate());
            if (dateInMillis == searchedDateInMillis) {
                listOfAvailableSeancesAtChosenDate.add(seance);
            }
        }
    }

    @FXML
    void onPressBackButton(ActionEvent event) {
        openNewScene(backButton, "../view/orderSecondStep.fxml", "Оформление заказа");
    }

    @FXML
    void onPressNextButton(ActionEvent event) throws IOException, ClassNotFoundException {
        if (checkElementInComboBoxIsChosen(timeComboBox)) {
            Seance chosenSeance = timeComboBox.getSelectionModel().getSelectedItem();
            Order order = new Order(Keeper.service, chosenSeance, Keeper.user);
            if (sendToServer(order, UserMessage.ADD_ORDER)) {
                Keeper.order = order;
                openNewScene(backButton,"../view/succesfullOrder.fxml", "Оформление заказа");
            }
        }
    }
}

