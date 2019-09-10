

package by.dyagel.controller;

import by.dyagel.Keeper;
import by.dyagel.controller.animation.Shake;
import by.dyagel.controller.messages.UserMessage;
import by.dyagel.model.entities.Role;
import by.dyagel.model.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SignUpController extends Controller {

    @FXML
    private TextField FIOField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButtonNext;

    @FXML
    private TextField EmailField;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private Button signUpButtonBack;

    @FXML
    private Label errorText;

    @FXML
    private TextField phoneNumberField;

    private ArrayList<TextField> listOfFields = new ArrayList<>();

    @FXML
    public void initialize() {
        listOfFields.add(FIOField);
        listOfFields.add(phoneNumberField);
        listOfFields.add(EmailField);
        listOfFields.add(passwordField);
        listOfFields.add(passwordField1);
    }

    @FXML
    void onPressButtonBack(ActionEvent event) {
        openNewScene(signUpButtonBack, "../view/startPage.fxml", "Барбершоп");
    }

    @FXML
    void onPressButtonNext(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        makeDefaultCondition(listOfFields, errorText);
        if (checkFieldsAreFilled(listOfFields)
                && checkPasswordsAreSame(passwordField, passwordField1, errorText)
                && checkUserFields(FIOField, phoneNumberField, EmailField, passwordField, errorText)) {
            signUpNewUser();
        }
    }


    private void signUpNewUser() throws SQLException, IOException, ClassNotFoundException {
        String FIO = FIOField.getText();
        String Email = EmailField.getText();
        String password = passwordField.getText();
        String phoneNumber = phoneNumberField.getText();
        User user = new User(FIO, Email, password, phoneNumber, Role.USER);
        if (!sendToServer(user, UserMessage.SIGN_UP)) {
            Shake.playAnimWithError(EmailField);
            setErrorText(errorText, "Почта уже занята");
        } else {
            Keeper.user = user;
            openNewScene(EmailField, "../view/userMenu.fxml", "Меню пользователя");
        }

    }

}
