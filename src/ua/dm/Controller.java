package ua.dm;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class Controller {

    private final int SPEED = 1;

    private int sum = 0;
    private Stage stage;

    @FXML
    private VBox vBox;
    @FXML
    private HBox box1;
    @FXML
    private Button b_;
    @FXML
    private Button bX;
    @FXML
    private VBox box2;
    @FXML
    private CheckBox cb12;
    @FXML
    private CheckBox cb42;
    @FXML
    private CheckBox cb201;
    @FXML
    private TextField tfAll;
    @FXML
    public VBox box3;
    @FXML
    public Button bChooser;
    @FXML
    private TreeView<String> treeView;
    private TreeItem<String> rootTree;

    // обробка активності CheckBox 12
    @FXML
    private void onSelect12(ActionEvent actionEvent) {
        onSelected(cb12);
    }

    // обробка активності CheckBox 42
    @FXML
    private void onSelect42(ActionEvent actionEvent) {
        onSelected(cb42);
    }

    // обробка активності CheckBox 201
    @FXML
    private void onSelect201(ActionEvent actionEvent) {
        onSelected(cb201);
    }

    // додавання/віднімання CheckBox
    private void onSelected(CheckBox cb) {

        if (cb.isSelected()) {
            sum = sum + Integer.parseInt(cb.getText());
        } else {
            sum = sum - Integer.parseInt(cb.getText());
        }

        tfAll.setText(String.valueOf(sum));
    }

    // обробка активності button X (закриття вікна) самопал
    @FXML
    private void onClickX(ActionEvent actionEvent) {
        shiftRightStage(stage, true);
    }

    // обробка активності button _ (міцнімізація вікна) самопал
    @FXML
    private void onClick_(ActionEvent actionEvent) {
        shiftRightStage(stage, false);
    }

    // зсув вікна вправо та приховування/закриття вікна
    private void shiftRightStage(Stage stage, boolean close) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = (int) stage.getX(); i <= Toolkit.getDefaultToolkit().getScreenSize().getWidth(); i++) {
                    stage.setX(i);
                    try {
                        Thread.sleep(SPEED);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (close) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.close();
                        }
                    });
                } else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.setIconified(true);
                        }
                    });

                }
            }
        }).start();
    }

    // зсув вікна вліво (відображення вікна)
    public void shiftLeftStage(Stage stage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = (int) stage.getX(); i >= Toolkit.getDefaultToolkit().getScreenSize().getWidth() - stage.getWidth(); i--) {
                    stage.setX(i);
                    try {
                        Thread.sleep(SPEED);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    // слухач події мінімізації/відновлення вікна, відображення вікна
    public void addListenerIconifiedProperty(Stage stage) {
        stage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> prop, Boolean oldValue, Boolean newValue) {
                shiftLeftStage(stage);
            }
        });
    }

    // Вибір директорії та побудова дерева каталогів та файлів
    @FXML
    private void onSelectDirectory(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(stage);

        if (file != null) {
            rootTree = new TreeItem<String>(file.getName());
            fileTree(file, rootTree);
            treeView = new TreeView<String>(rootTree);
            treeView.edit(rootTree);
            box3.getChildren().remove(2);
            box3.getChildren().add(2, treeView);
        }
    }

    // побудова дерева каталогів та файлів
    private void fileTree(File file, TreeItem<String> rootTree) {

        for (File fileNext : file.listFiles()) {

            TreeItem<String> tree = new TreeItem<String>(fileNext.getName());
            rootTree.getChildren().addAll(tree);

            if (fileNext.isDirectory()) {
                fileTree(fileNext, tree);
            }
        }
    }

    // встановлення фокуса на елемент Directory Chooser
    public void focus() {
        bChooser.requestFocus();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}