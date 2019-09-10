package by.dyagel.controller;

import by.dyagel.Checker;
import by.dyagel.controller.animation.Shake;
import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.controller.messages.UserMessage;
import by.dyagel.model.entities.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;

import java.io.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ManageServicesController extends Controller {
    private ObservableList<Service> servicesData;

    @FXML
    private TableView<Service> serviceTable;

    @FXML
    private TableColumn<Service, Integer> idColumn;

    @FXML
    private TableColumn<Service, String> categoryColumn;

    @FXML
    private TableColumn<Service, String> serviceColumn;

    @FXML
    private TableColumn<Service, BigDecimal> priceColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addButton;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField serviceField;

    @FXML
    private TextField priceField;

    @FXML
    private Label text;

    @FXML
    private Button backButton;

    private ArrayList<TextField> listOfFields = new ArrayList<>();

    private ArrayList<Service> listOfServices;


    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        servicesData = FXCollections.observableArrayList();
        listOfFields.add(categoryField);
        listOfFields.add(serviceField);
        listOfFields.add(priceField);

        listOfServices = initListWithDataFromServer(listOfServices, UserMessage.GET_SERVICES);
        servicesData.addAll(listOfServices);


        idColumn.setCellValueFactory(new PropertyValueFactory<Service, Integer>("id"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("category"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Service, BigDecimal>("price"));

        serviceTable.setItems(servicesData);
        serviceTable.setEditable(true);
        categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        serviceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));

    }


    private int getAvailableId(ArrayList<Service> listOfServices) {
        return listOfServices.size() == 0 ? 1 : listOfServices.get(listOfServices.size() - 1).getId() + 1;
    }

    @FXML
    void onPressAddButton(ActionEvent event) throws IOException, ClassNotFoundException {

        makeDefaultCondition(listOfFields, text);
        if (checkFieldsAreFilled(listOfFields) && checkFieldIsNumeric(priceField) && checkPriceIsPositive(priceField)) {
            Service service = new Service();
            service.setId(getAvailableId(listOfServices));
            service.setCategory(categoryField.getText());
            service.setName(serviceField.getText());
            service.setPrice(BigDecimal.valueOf(Double.parseDouble(priceField.getText())));

            if (sendToServer(service, UserMessage.ADD_SERVICE)) {
                serviceTable.getItems().add(service);
                clearFields(listOfFields);
            }
        }
        initialize();
    }

    boolean checkFieldIsNumeric(TextField field) {
        if (!Checker.isStringConvertibleToNumeric(field.getText())) {
            Shake.playAnimWithError(field);
            setErrorText(text, "Цена должна быть числом!");
            return false;
        }
        return true;
    }

    boolean checkPriceIsPositive(TextField field) {
        if (!Checker.isPositive(BigDecimal.valueOf(Double.parseDouble(field.getText())))) {
            Shake.playAnimWithError(field);
            setErrorText(text, "Цена должна быть положительной!");
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

        ObservableList<Service> selectedServices = serviceTable.getSelectionModel().getSelectedItems();
        ObservableList<Service> services = serviceTable.getItems();
        for (Service service : selectedServices) {
            if (sendToServer(service, UserMessage.DELETE_SERVICE)) {
                services.remove(service);
            }
        }
        initialize();
    }

    public void onEditCategory(TableColumn.CellEditEvent<Service, String> serviceStringCellEditEvent)
            throws IOException, ClassNotFoundException {
        Service service = serviceTable.getSelectionModel().getSelectedItem();
        service.setCategory(serviceStringCellEditEvent.getNewValue());
        sendToServer(service,UserMessage.UPDATE_SERVICE);
        initialize();

    }

    public void onEditService(TableColumn.CellEditEvent<Service, String> serviceStringCellEditEvent)
            throws IOException, ClassNotFoundException {
        Service service = serviceTable.getSelectionModel().getSelectedItem();
        service.setName(serviceStringCellEditEvent.getNewValue());
        sendToServer(service,UserMessage.UPDATE_SERVICE);
        initialize();
    }

    public void onEditPrice(TableColumn.CellEditEvent<Service, BigDecimal> serviceBigDecimalCellEditEvent)
            throws IOException, ClassNotFoundException {
        Service service = serviceTable.getSelectionModel().getSelectedItem();
        service.setPrice(serviceBigDecimalCellEditEvent.getNewValue());
        sendToServer(service,UserMessage.UPDATE_SERVICE);
        initialize();
    }

}

