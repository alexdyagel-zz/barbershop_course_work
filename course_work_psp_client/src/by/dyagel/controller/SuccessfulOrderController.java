package by.dyagel.controller;

import by.dyagel.Keeper;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SuccessfulOrderController extends Controller {

    @FXML
    private TextField specialistTextField;

    @FXML
    private TextField serviceTextField;

    @FXML
    private TextField dateTextField;

    @FXML
    private TextField timeTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private Button backButton;

    @FXML
    private Button reportButton;

    @FXML
    private TextField reportTextField;

    @FXML
    private Text textReportSuccess;

    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        specialistTextField.setText(Keeper.order.getSeance().getSpecialist().toString());
        serviceTextField.setText(Keeper.order.getService().getName());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        dateTextField.setText(sdf.format(Keeper.order.getSeance().getDate()));
        timeTextField.setText(Keeper.order.getSeance().getTime().toString());
        priceTextField.setText(Keeper.order.getService().getPrice().toString() + " р");

        specialistTextField.setEditable(false);
        serviceTextField.setEditable(false);
        dateTextField.setEditable(false);
        timeTextField.setEditable(false);
        priceTextField.setEditable(false);

    }

    @FXML
    void onPressBackButton(ActionEvent event) {
        openNewScene(backButton, "../view/userMenu.fxml", "Меню пользователя");
    }

    @FXML
    void onPressMakeReportButton(ActionEvent event) {
        if (checkFieldIsFilled(reportTextField)) {
            createReport();
            textReportSuccess.setVisible(true);
            reportTextField.clear();
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(e -> {
                textReportSuccess.setVisible(false);
            });
            delay.play();
        }
    }

    void createReport(){
        String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setDecimalSeparator('.');
        DecimalFormat f = new DecimalFormat("#,##0.00", s);

        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph0 = document.createParagraph();
        XWPFRun run0 = paragraph0.createRun();
        run0.setText("Запись на услугу в AnStudio");
        run0.setFontSize(14);
        run0.setBold(true);
        run0.setFontFamily("Times New Roman");
        paragraph0.setAlignment(ParagraphAlignment.CENTER);
        paragraph0.setSpacingAfter(500);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("-------------------------------------------------------");
        run.addBreak();
        run.setText("Данные о записи");
        run.setFontFamily("Times New Roman");
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setSpacingAfter(500);

        XWPFParagraph paragraph1 = document.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setText("Мастер: " + specialistTextField.getText());
        run1.addBreak();
        run1.setText("Услуга: " + serviceTextField.getText());
        run1.addBreak();
        run1.setText("Цена: " + priceTextField.getText());
        run1.addBreak();
        run1.setText("Дата: " + dateTextField.getText());
        run1.addBreak();
        run1.setText("Время: " + timeTextField.getText());
        run1.setFontSize(14);
        run1.setFontFamily("Times New Roman");
        paragraph1.setSpacingAfter(250);

        XWPFParagraph paragraph2 = document.createParagraph();
        XWPFRun run2 = paragraph2.createRun();
        run2.addBreak();
        run2.setText("-------------------------------------------------------");
        run2.addBreak();
        run2.setText("Клиент");
        paragraph2.setAlignment(ParagraphAlignment.CENTER);


        XWPFParagraph paragraph3 = document.createParagraph();
        XWPFRun run3 = paragraph3.createRun();
        run3.setText("Имя: " + Keeper.user.getFIO());
        run3.addBreak();
        run3.setText("Электронная почта: " + Keeper.user.getEmail());
        run3.addBreak();
        run3.setText("Телефон: " + Keeper.user.getPhoneNumber());
        run3.addBreak();
        run3.setFontSize(14);
        run3.setFontFamily("Times New Roman");
        paragraph3.setAlignment(ParagraphAlignment.LEFT);

        XWPFParagraph paragraph4 = document.createParagraph();
        XWPFRun run4 = paragraph4.createRun();
        run4.addBreak();
        run4.setText("-------------------------------------------------------");
        run4.addBreak();
        paragraph4.setAlignment(ParagraphAlignment.CENTER);


        XWPFParagraph paragraph5 = document.createParagraph();
        XWPFRun run5 = paragraph5.createRun();
        run5.addBreak();
        run5.setText("Дата заказа: " + date);
        run5.addBreak();
        run5.setFontSize(14);
        paragraph5.setAlignment(ParagraphAlignment.RIGHT);

        try {
            FileOutputStream output = new FileOutputStream(reportTextField.getText() + ".docx");
            document.write(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

