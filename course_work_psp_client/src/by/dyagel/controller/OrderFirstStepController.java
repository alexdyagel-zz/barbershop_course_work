package by.dyagel.controller;

import by.dyagel.Keeper;
import by.dyagel.controller.messages.UserMessage;
import by.dyagel.model.entities.Service;
import by.dyagel.model.entities.Specialist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;

public class OrderFirstStepController extends Controller {

    @FXML
    private Label text;

    @FXML
    private ComboBox<Service> serviceComboBox;

    @FXML
    private ComboBox<Specialist> specialistCombobox;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;

    private ObservableList<Specialist> specialistsData;

    private ObservableList<Service> servicesData;

    private ArrayList<Specialist> listOfSpecialists;

    private ArrayList<Service> listOfServices;

    private ArrayList<ComboBox> listOfComboBoxes = new ArrayList<>();

    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        specialistsData = FXCollections.observableArrayList();
        servicesData = FXCollections.observableArrayList();

        listOfServices = initListWithDataFromServer(listOfServices, UserMessage.GET_SERVICES);
        listOfSpecialists = initListWithDataFromServer(listOfSpecialists, UserMessage.GET_SPECIALISTS);

        specialistsData.addAll(listOfSpecialists);
        servicesData.addAll(listOfServices);

        specialistCombobox.setItems(specialistsData);
        serviceComboBox.setItems(servicesData);

        listOfComboBoxes.add(specialistCombobox);
        listOfComboBoxes.add(serviceComboBox);
    }

    @FXML
    void onPressBackButton(ActionEvent event) {
        openNewScene(backButton, "../view/userMenu.fxml", "Меню пользователя");
    }

    @FXML
    void onPressNextButton(ActionEvent event) {
        if (checkElementInComboBoxIsChosen(serviceComboBox)
                && checkElementInComboBoxIsChosen(specialistCombobox)) {
            Keeper.service = serviceComboBox.getSelectionModel().getSelectedItem();
            Keeper.specialist = specialistCombobox.getSelectionModel().getSelectedItem();
            openNewScene(backButton,"../view/orderSecondStep.fxml", "ОФормление заказа");
        }
    }

}
