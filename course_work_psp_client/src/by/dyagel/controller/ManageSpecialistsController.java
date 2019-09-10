package by.dyagel.controller;

import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.controller.messages.UserMessage;
import by.dyagel.model.entities.Specialist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ManageSpecialistsController extends Controller {

    @FXML
    private TableView<Specialist> specialistTable;

    @FXML
    private TableColumn<Specialist, Integer> idColumn;

    @FXML
    private TableColumn<Specialist, String> positionColumn;

    @FXML
    private TableColumn<Specialist, String> nameColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addButton;

    @FXML
    private TextField positionField;

    @FXML
    private TextField nameField;

    @FXML
    private Label text;

    private ObservableList<Specialist> specialistsData;

    private ArrayList<TextField> listOfFields = new ArrayList<>();

    private ArrayList<Specialist> listOfSpecialists;

    @FXML
    private Button backButton;

    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        specialistsData = FXCollections.observableArrayList();
        listOfFields.add(positionField);
        listOfFields.add(nameField);

        listOfSpecialists = initListWithDataFromServer(listOfSpecialists, UserMessage.GET_SPECIALISTS);
        specialistsData.addAll(listOfSpecialists);


        idColumn.setCellValueFactory(new PropertyValueFactory<Specialist, Integer>("id"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<Specialist, String>("position"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Specialist, String>("name"));

        specialistTable.setItems(specialistsData);
        specialistTable.setEditable(true);
        positionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }


    private int getAvailableId(ArrayList<Specialist> listOfSpecialists) {
        return listOfSpecialists.size() == 0 ? 1 : listOfSpecialists.get(listOfSpecialists.size() - 1).getId() + 1;
    }

    @FXML
    void onPressAddButton(ActionEvent event) throws IOException, ClassNotFoundException {
        makeDefaultCondition(listOfFields, text);
        if (checkFieldsAreFilled(listOfFields)) {
            Specialist specialist = new Specialist();
            specialist.setId(getAvailableId(listOfSpecialists));
            specialist.setPosition(positionField.getText());
            specialist.setName(nameField.getText());

            if (sendToServer(specialist, UserMessage.ADD_SPECIALIST)) {
                specialistTable.getItems().add(specialist);
                clearFields(listOfFields);
            }
        }
        initialize();
    }

    @FXML
    void onPressBackButton(ActionEvent event) {
        openNewScene(backButton, "../view/adminMenu.fxml", "Меню администратора");
    }

    @FXML
    void onPressDeleteButton(ActionEvent event) throws IOException, ClassNotFoundException {
        ObservableList<Specialist> selectedSpecialists = specialistTable.getSelectionModel().getSelectedItems();
        ObservableList<Specialist> specialists = specialistTable.getItems();
        for (Specialist specialist : selectedSpecialists) {
            if (sendToServer(specialist, UserMessage.DELETE_SPECIALIST)) {
                specialists.remove(specialist);
            }
        }
        initialize();
    }

    public void onEditPosition(TableColumn.CellEditEvent<Specialist, String> specialistStringCellEditEvent)
            throws IOException, ClassNotFoundException {
        Specialist specialist = specialistTable.getSelectionModel().getSelectedItem();
        specialist.setName(specialistStringCellEditEvent.getNewValue());
        sendToServer(specialist, UserMessage.UPDATE_SPECIALIST);
        initialize();
    }

    public void onEditName(TableColumn.CellEditEvent<Specialist, String> specialistStringCellEditEvent)
            throws IOException, ClassNotFoundException {
        Specialist specialist = specialistTable.getSelectionModel().getSelectedItem();
        specialist.setName(specialistStringCellEditEvent.getNewValue());
        sendToServer(specialist, UserMessage.UPDATE_SPECIALIST);
        initialize();
    }
}
