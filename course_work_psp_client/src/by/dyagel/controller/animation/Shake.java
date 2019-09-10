package by.dyagel.controller.animation;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
    public static void playAnim(Node node) {
        TranslateTransition tt;
        tt = new TranslateTransition(Duration.millis(70), node);
        tt.setFromX(0f);
        tt.setByX(10f);
        tt.setCycleCount(4);
        tt.setAutoReverse(true);
        tt.playFromStart();
    }
    public static void playAnimWithError(Node node){
        playAnim(node);
        node.setStyle("-fx-background-color: #f0820c;");
    }
}
