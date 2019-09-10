package by.dyagel.controller;

import by.dyagel.Checker;
import by.dyagel.controller.animation.Shake;
import by.dyagel.controller.messages.UserMessage;
import by.dyagel.model.entities.Seance;
import by.dyagel.model.entities.Specialist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

public class ManageSeancesController extends Controller {

    @FXML
    private TableView<Seance> seancesTable;

    @FXML
    private TableColumn<Seance, Integer> idColumn;

    @FXML
    private TableColumn<Seance, Date> dateColumn;

    @FXML
    private TableColumn<Seance, Time> timeColumn;

    @FXML
    private TableColumn<Seance, Specialist> specialistColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addButton;

    @FXML
    private Label text;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField hoursTextField;

    @FXML
    private TextField minutesTextField;

    @FXML
    private ComboBox<Specialist> specialistComboBox;

    @FXML
    private Button backButton;

    private ObservableList<Seance> seancesData;

    private ObservableList<Specialist> specialistsData;

    private ArrayList<Seance> listOfSeances = new ArrayList<>();

    private ArrayList<Specialist> listOfSpecialists = new ArrayList<>();

    private ArrayList<TextField> listOfFields = new ArrayList<>();

    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        datePicker.setEditable(false);
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        seancesData = FXCollections.observableArrayList();
        specialistsData = FXCollections.observableArrayList();
        listOfFields.add(minutesTextField);
        listOfFields.add(hoursTextField);

        listOfSpecialists = initListWithDataFromServer(listOfSpecialists, UserMessage.GET_SPECIALISTS);
        listOfSeances = initListWithDataFromServer(listOfSeances, UserMessage.GET_SEANCES);
        seancesData.addAll(listOfSeances);
        specialistsData.addAll(listOfSpecialists);

        specialistComboBox.setItems(specialistsData);
        idColumn.setCellValueFactory(new PropertyValueFactory<Seance, Integer>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Seance, Date>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Seance, Time>("time"));
        specialistColumn.setCellValueFactory(new PropertyValueFactory("specialist"));

        seancesTable.setItems(seancesData);
    }

    @FXML
    void onPressAddButton(ActionEvent event) throws IOException, ClassNotFoundException {
        makeDefaultCondition(listOfFields, text);
        if (checkDateIsChosen(datePicker)
                && checkFieldIsFilled(hoursTextField)
                && checkFieldIsFilled(minutesTextField)
                && checkElementInComboBoxIsChosen(specialistComboBox)
                && checkTextFieldContainNumber(hoursTextField, minutesTextField)
                && checkTimeIsCorrect(hoursTextField, minutesTextField)) {
            Seance seance = new Seance();
            seance.setId(getAvailableId(listOfSeances));
            seance.setTime(Integer.parseInt(hoursTextField.getText()), Integer.parseInt(minutesTextField.getText()));
            seance.setDate(Date.valueOf(datePicker.getValue()));
            seance.setSpecialist(specialistComboBox.getSelectionModel().getSelectedItem());

            if (sendToServer(seance, UserMessage.ADD_SEANCE)) {
                System.out.println("hui");
                seancesTable.getItems().add(seance);
                clearFields(listOfFields);
                specialistComboBox.getSelectionModel().clearSelection();
                datePicker.getEditor().clear();
            }
        }
        initialize();
    }

    private int getAvailableId(ArrayList<Seance> listOfSeances) {
        return listOfSeances.size() == 0 ? 1 : listOfSeances.get(listOfSeances.size() - 1).getId() + 1;
    }

    boolean checkTimeIsCorrect(TextField hoursTextField, TextField minutesTextField) {
        if (!Checker.areHoursCorrect(Integer.parseInt(hoursTextField.getText()))
                || !Checker.areMinutesCorrect(Integer.parseInt(minutesTextField.getText()))) {
            Shake.playAnimWithError(hoursTextField);
            Shake.playAnimWithError(minutesTextField);
            setErrorText(text, "Неверный формат времени!");
            return false;
        }
        return true;
    }

    boolean checkTextFieldContainNumber(TextField hoursTextField, TextField minutesTextField) {
        if (!Checker.isStringConvertibleToNumeric(hoursTextField.getText())
                || !Checker.isStringConvertibleToNumeric(minutesTextField.getText())) {
            Shake.playAnimWithError(minutesTextField);
            Shake.playAnimWithError(hoursTextField);
            setErrorText(text, "Неверный формат времени!");
            return false;
        }
        return true;
    }


    @FXML
    void onPressBackButton(ActionEvent event) {
        openNewScene(backButton, "../view/adminMenu.fxml", "Меню администратора");
    }

    @FXML
    void onPressDeleteButton(ActionEvent event) throws IOException, ClassNotFoundException {
        ObservableList<Seance> selectedSeances = seancesTable.getSelectionModel().getSelectedItems();
        ObservableList<Seance> seances = seancesTable.getItems();
        for (Seance seance : selectedSeances) {
            if (sendToServer(seance, UserMessage.DELETE_SEANCE)) {
                System.out.println("hui");
                seances.remove(seance);
            }
        }
        initialize();
    }
}
