package ua.dm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

/**
 * @author Maksym Stetsenko
 */

public class AllyNote extends Application {

    private Controller controller;
    private int dolonya = 250;

    @Override
    public void start(Stage stage) throws Exception {
        // Устанавливается заголовок основного окна JavaFX-приложения:
        stage.setTitle("AllyNote");
        // Створюється кореневий вузол графа сцени з fxml
        String fxmlFile = "allynote.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        // Отримуємо роздільну здатність екрана
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // створюємо екземпляр сцени
        Scene scene = new Scene(root, dolonya, screenSize.getHeight());
        // застосовуємо  css-стилі
        scene.getStylesheets().add(("ua/dm/style.css"));
        // встановлюємо scene на stege
        stage.setScene(scene);
        // заборона зміни розмірів вікна
        stage.setResizable(false);
        // розміщення вікна, встановлення координат
        stage.setX(screenSize.getWidth());
        stage.setY(0);
        // встановлення стилю без елементів управління та оформлення
        stage.initStyle(StageStyle.UNDECORATED);
        // відображення
        stage.show();

        // отримання контролера керування елементами вікна
        controller = loader.getController();
        // передача stage контролеру
        controller.setStage(stage);
        // анімація екрана - випливає вліво
        controller.shiftLeftStage(stage);
        // слухач мінімізації
        controller.addListenerIconifiedProperty(stage);
        // встановлення фокусу на елемент
        controller.focus();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
