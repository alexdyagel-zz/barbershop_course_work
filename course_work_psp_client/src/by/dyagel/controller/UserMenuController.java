package by.dyagel.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UserMenuController extends Controller{

    @FXML
    private Button orderButton;

    @FXML
    private Button manageAccount;

    @FXML
    private Button backButton;

    @FXML
    void onPressBackButton(ActionEvent event) {
        openNewScene(backButton,"../view/startPage.fxml", "Барбершоп");
    }

    @FXML
    void onPressManageAccount(ActionEvent event) {
        openNewScene(backButton,"../view/manageAccount.fxml", "Учетная запись");
    }

    @FXML
    void onPressOrderButton(ActionEvent event) {
        openNewScene(backButton,"../view/orderFirstStep.fxml", "Оформление записи");
    }

}
