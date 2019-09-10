package by.dyagel.controller;

import by.dyagel.Checker;
import by.dyagel.controller.animation.Shake;
import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.controller.messages.UserMessage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

class Controller<T> {
    void openNewScene(Node element, String window, String title) {
        element.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }

    boolean isFieldEmpty(TextField field) {
        return field.getText().trim().isEmpty();
    }

    void setErrorText(Label text, String error) {
        text.setVisible(true);
        text.setText(error);
    }

    void makeDefaultTextFieldStyle(TextField field) {
        field.setStyle("-fx-background-color: #fafafa;");
        field.setStyle("-fx-border-color: silver;");
        field.setStyle("-fx-border-radius: 5;");
    }

    boolean areFieldsSame(TextField field1, TextField field2) {
        return field1.getText().trim().equals(field2.getText().trim());
    }

    void doOnWrongTextFieldData(TextField field, Label label, String errorMessage) {
        Shake.playAnimWithError(field);
        setErrorText(label, errorMessage);
    }

    boolean isFIOFieldCorrect(TextField FIOField) {
        return Checker.isFIOCorrect(FIOField.getText());
    }

    boolean isPhoneNumberFieldCorrect(TextField phoneNumberField) {
        return Checker.isPhoneNumberCorrect(phoneNumberField.getText());
    }

    boolean isEmailFieldCorrect(TextField EmailField) {
        return Checker.isEmailCorrect(EmailField.getText());
    }

    boolean isPasswordFieldCorrect(PasswordField passwordField) {
        return Checker.isPasswordCorrect(passwordField.getText());
    }

    boolean checkFieldsAreFilled(ArrayList<TextField> listOfFields) {
        for (TextField field : listOfFields) {
            if (isFieldEmpty(field)) {
                Shake.playAnim(field);
                return false;
            }
        }
        return true;
    }

    boolean checkFieldIsFilled(TextField field) {
        if (isFieldEmpty(field)) {
            Shake.playAnim(field);
            return false;
        }
        return true;
    }

    boolean checkElementInComboBoxIsChosen(ComboBox combobox) {
        if (combobox.getSelectionModel().isEmpty()) {
            Shake.playAnim(combobox);
            return false;
        }
        return true;
    }

    boolean checkDateIsChosen(DatePicker datePicker) {
        if (datePicker.getValue() == null) {
            Shake.playAnim(datePicker);
            return false;
        }
        return true;
    }

    void makeDefaultCondition(ArrayList<TextField> listOfFields, Label errorText) {
        for (TextField field : listOfFields) {
            makeDefaultTextFieldStyle(field);
        }
        errorText.setVisible(false);
        errorText.setTextFill(Color.web("#ee0808"));

    }

    void clearFields(ArrayList<TextField> listOfFields) {
        for (TextField field : listOfFields) {
            field.clear();
        }
    }

    boolean checkPasswordsAreSame(PasswordField pass1, PasswordField pass2, Label errorText) {
        if (!areFieldsSame(pass1, pass2)) {
            doOnWrongTextFieldData(pass1, errorText, "Пароли не совпадают");
            doOnWrongTextFieldData(pass2, errorText, "Пароли не совпадают");
            return false;
        }
        return true;
    }

    boolean checkUserFields(TextField FIOField, TextField phoneNumberField, TextField EmailField,
                            PasswordField passwordField, Label errorText) {
        if (!isFIOFieldCorrect(FIOField)) {
            doOnWrongTextFieldData(FIOField, errorText, "Неверный формат ФИО");
            return false;
        } else if (!isPhoneNumberFieldCorrect(phoneNumberField)) {
            Shake.playAnimWithError(phoneNumberField);
            doOnWrongTextFieldData(phoneNumberField, errorText,
                    "Неверный номер телефона. Верный формат: (+375/80)(29/25/44/33)XXXXXXX");
            return false;
        } else if (!isEmailFieldCorrect(EmailField)) {
            doOnWrongTextFieldData(EmailField, errorText, "Неверный формат Email");
            return false;
        } else if (!isPasswordFieldCorrect(passwordField)) {
            doOnWrongTextFieldData(passwordField, errorText,
                    "Пароль должен включать как минимум 8 символов, цифры, верхний и нижний регистр");
            return false;
        }
        return true;
    }

    ArrayList<T> initListWithDataFromServer(ArrayList<T> list, UserMessage getMessage) throws IOException {
        Socket sock = null;
        InputStream is = null;
        OutputStream os = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        list = new ArrayList<>();
        try {
            sock = new Socket(InetAddress.getByName("localhost"), 1024);
            is = sock.getInputStream();
            os = sock.getOutputStream();
            oos = new ObjectOutputStream(os);
            ois = new ObjectInputStream(is);
            oos.writeObject(getMessage);
            list = (ArrayList<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (ois != null) {
                ois.close();
            }
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
            if (sock != null) {
                sock.close();
            }
        }
        return list;
    }

    boolean sendToServer(T entity, UserMessage addMessage) throws IOException, ClassNotFoundException {
        Socket sock = new Socket(InetAddress.getByName("localhost"), 1024);
        InputStream is = sock.getInputStream();
        OutputStream os = sock.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        ObjectInputStream ois = new ObjectInputStream(is);
        oos.writeObject(addMessage);
        oos.writeObject(entity);
        ServerResponse response = (ServerResponse) ois.readObject();
        oos.close();
        ois.close();
        return response.equals(ServerResponse.ACCEPTED);
    }

    public static long getDatePart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    void initPieChart(PieChart pie, Label caption) {
        for (final PieChart.Data data : pie.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    e -> {
                        double total = 0;
                        for (PieChart.Data d : pie.getData()) {
                            total += d.getPieValue();
                        }
                        caption.setTranslateX(e.getSceneX() - 45);
                        caption.setTranslateY(e.getSceneY() - 330);
                        String text = String.format("%.1f%%", 100 * data.getPieValue() / total);
                        caption.setText(text);
                    });
        }
    }

}
