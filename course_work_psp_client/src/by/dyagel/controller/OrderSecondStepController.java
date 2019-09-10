package by.dyagel.controller;

import by.dyagel.Keeper;
import by.dyagel.controller.messages.UserMessage;
import by.dyagel.model.entities.Seance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderSecondStepController extends Controller {

    @FXML
    private Text text;

    @FXML
    private Button nextButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button backButton;

    private ArrayList<Seance> listOfSeances = new ArrayList<>();

    private ArrayList<LocalDate> listOfDates = new ArrayList<>();


    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        initListOfSeances();
        initAvailableDates();

        datePicker.setEditable(false);
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(!listOfDates.contains(date) || empty || date.compareTo(today) < 0);
            }
        });
    }

    private void initAvailableDates() {
        for (Seance seance : listOfSeances) {
            listOfDates.add(seance.getDate().toLocalDate());
        }
    }

    private void initListOfSeances() throws IOException, ClassNotFoundException {
        Socket sock;
        InputStream is;
        OutputStream os;
        try {
            sock = new Socket(InetAddress.getByName("localhost"), 1024);
            is = sock.getInputStream();
            os = sock.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            ObjectInputStream ois = new ObjectInputStream(is);
            oos.writeObject(UserMessage.FIND_AVAILABLE_SEANCES_OF_SPECIALIST);
            oos.writeObject(Keeper.specialist);
            listOfSeances = (ArrayList<Seance>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onPressBackButton(ActionEvent event) {
        openNewScene(backButton, "../view/orderFirstStep.fxml", "Оформление заказа");
    }

    @FXML
    void onPressNextButton(ActionEvent event) {
        if (checkDateIsChosen(datePicker)) {
            Keeper.listOfSeances = listOfSeances;
            Keeper.date = Date.valueOf(datePicker.getValue());
            openNewScene(backButton, "../view/orderThirdStep.fxml", "Оформление заказа");
        }
    }
}
