package by.dyagel.controller;

import by.dyagel.Keeper;
import by.dyagel.controller.animation.Shake;
import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.controller.messages.UserMessage;
import by.dyagel.model.entities.Role;
import by.dyagel.model.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;

public class StartPageController extends Controller {

    @FXML
    private TextField EmailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Text errorText;

    @FXML
    private Button signUpButton;

    @FXML
    private Button exitButton;


    private void loginUser(String loginEmail, String loginPassword) throws SQLException, IOException {
        Socket sock;
        InputStream is = null;
        OutputStream os = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            sock = new Socket(InetAddress.getByName("localhost"), 1024);
            is = sock.getInputStream();
            os = sock.getOutputStream();
            oos = new ObjectOutputStream(os);
            ois = new ObjectInputStream(is);
            oos.writeObject(UserMessage.SIGN_IN);
            oos.writeObject(new User(loginEmail, loginPassword));
            ServerResponse response = (ServerResponse) ois.readObject();
            if (response.equals(ServerResponse.ACCEPTED)) {
                User user = (User) ois.readObject();
                if (user.getRole().equals(Role.USER)) {
                    Keeper.user = user;
                    openNewScene(signUpButton, "../view/userMenu.fxml", "Меню пользователя");
                } else {
                    openNewScene(signUpButton, "../view/adminMenu.fxml", "Меню администратора");
                }
            } else {
                errorText.setVisible(true);
                Shake.playAnim(EmailField);
                Shake.playAnim(passwordField);
                passwordField.setStyle("-fx-background-color: #f0820c;");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (ois != null) {
                ois.close();
            }
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    public void onPressExitButton(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void onPressSignUpButton(javafx.event.ActionEvent actionEvent) throws Exception {
        openNewScene(signUpButton, "../view/signUp.fxml", "Регистрация");
    }

    public void onPressLoginButton(javafx.event.ActionEvent actionEvent) {
        String loginEmail = EmailField.getText().trim();
        String loginPassword = passwordField.getText().trim();
        if (!loginEmail.isEmpty() && !loginPassword.isEmpty()) {
            try {
                loginUser(loginEmail, loginPassword);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        } else {
            Shake.playAnim(EmailField);
            Shake.playAnim(passwordField);
        }
    }
}
