package by.dyagel.controller;

import by.dyagel.Keeper;
import by.dyagel.controller.animation.Shake;
import by.dyagel.controller.messages.UserMessage;
import by.dyagel.model.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;

public class ManageAccountController extends Controller {

    @FXML
    private TextField FIOField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField EmailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private Button saveButton;

    @FXML
    private Label errorText;

    @FXML
    private Button backButton;

    private ArrayList<TextField> listOfFields = new ArrayList<>();


    @FXML
    public void initialize() {
        listOfFields.add(FIOField);
        listOfFields.add(phoneNumberField);
        listOfFields.add(EmailField);
        listOfFields.add(passwordField);
        listOfFields.add(passwordField1);

        FIOField.setText(Keeper.user.getFIO());
        phoneNumberField.setText(Keeper.user.getPhoneNumber());
        EmailField.setText(Keeper.user.getEmail());
        passwordField.setText(Keeper.user.getPassword());
        passwordField1.setText(Keeper.user.getPassword());
    }

    @FXML
    void onPressBackButton(ActionEvent event) {
        openNewScene(backButton, "../view/userMenu.fxml", "Меню пользователя");
    }

    @FXML
    void onPressSaveButton(ActionEvent event) throws IOException, ClassNotFoundException {
        makeDefaultCondition(listOfFields, errorText);
        if (checkFieldsAreFilled(listOfFields)
                && checkPasswordsAreSame(passwordField, passwordField1, errorText)
                && checkUserFields(FIOField, phoneNumberField, EmailField, passwordField, errorText)) {
            updateInfo();
        }
    }

    private void updateInfo() throws IOException, ClassNotFoundException {
        User user = Keeper.user;
        user.setEmail(EmailField.getText());
        user.setPassword(passwordField.getText());
        user.setPhoneNumber(phoneNumberField.getText());
        user.setFIO(FIOField.getText());
        if (!sendToServer(user, UserMessage.UPDATE_USER)) {
            Shake.playAnimWithError(EmailField);
            setErrorText(errorText, "Почта уже занята");
        } else {
            Keeper.user = user;
            setErrorText(errorText, "Данные успешно изменены");
            errorText.setTextFill(Color.web("#0076a3"));
        }
    }
}
