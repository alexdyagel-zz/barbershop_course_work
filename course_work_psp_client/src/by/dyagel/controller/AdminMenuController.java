package by.dyagel.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminMenuController extends Controller {

    @FXML
    private Button serviceButton;

    @FXML
    private Button clientsButton;

    @FXML
    private Button seancesButton;

    @FXML
    private Button orderButton;

    @FXML
    private Button specialistsButton;

    @FXML
    private Button backButton;

    @FXML
    void onPressBackButton(ActionEvent event) {
        openNewScene(backButton, "../view/startPage.fxml", "Барбершоп");
    }

    @FXML
    void onPressClientsButton(ActionEvent event) {
        openNewScene(backButton, "../view/manageClients.fxml", "Клиенты");
    }

    @FXML
    void onPressOrderButton(ActionEvent event) {
        openNewScene(backButton, "../view/manageOrders.fxml", "Заказы");
    }

    @FXML
    void onPressSeancesButton(ActionEvent event) {
        openNewScene(backButton, "../view/manageSeances.fxml", "Сеансы");
    }

    @FXML
    void onPressServiceButton(ActionEvent event) {
        openNewScene(backButton, "../view/manageServices.fxml", "Услуги");
    }

    @FXML
    void onPressSpecialistsButton(ActionEvent event) {
        openNewScene(backButton, "../view/manageSpecialists.fxml", "Специалисты");
    }

}
