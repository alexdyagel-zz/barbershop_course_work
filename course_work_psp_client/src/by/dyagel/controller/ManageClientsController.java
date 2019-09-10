package by.dyagel.controller;

import by.dyagel.controller.messages.UserMessage;
import by.dyagel.model.entities.Role;
import by.dyagel.model.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

public class ManageClientsController extends Controller {
    private ObservableList<User> usersData = FXCollections.observableArrayList();

    @FXML
    private TableView<User> clientTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> FIOColumn;

    @FXML
    private TableColumn<User, String> phoneNumberColumn;

    @FXML
    private TableColumn<User, String> EmailColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    private ArrayList<User> listOfUsers;


    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        listOfUsers = initListWithDataFromServer(listOfUsers, UserMessage.GET_USERS);
        for (User user : listOfUsers) {
            if (user.getRole().equals(Role.USER)) {
                usersData.add(user);
            }
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        FIOColumn.setCellValueFactory(new PropertyValueFactory<User, String>("FIO"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("phoneNumber"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
        clientTable.setItems(usersData);
    }


    @FXML
    void onPressBackButton(ActionEvent event) {
        openNewScene(backButton, "../view/adminMenu.fxml", "Меню администратора");
    }

    @FXML
    void onPressDeleteButton(ActionEvent event) throws IOException, ClassNotFoundException {
        ObservableList<User> selectedUsers = clientTable.getSelectionModel().getSelectedItems();
        ObservableList<User> users = clientTable.getItems();
        for (User user : selectedUsers) {
            if (sendToServer(user, UserMessage.DELETE_USER)) {
                users.remove(user);
            }
        }
    }
}
