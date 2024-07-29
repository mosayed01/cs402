package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class HelloApplication extends Application {

    final static Button selectButton = new Button("Select a file to reverse");
    final static Button reverseButton = new Button("Reverse text");
    final static Button saveButton = new Button("Save file");
    final static TextArea textArea = new TextArea();


    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        HBox hBox = new HBox();
        hBox.getChildren().addAll(selectButton, reverseButton, saveButton);
        root.getChildren().addAll(textArea, hBox);

        setupSelectButton(stage);
        setupReverseButton();
        setupSaveButton();

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("CS 402");
        stage.setScene(scene);
        stage.show();
    }

    private void setupSelectButton(Stage stage) {
        selectButton.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(stage);
            if (file == null) {
                return;
            }

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    textArea.appendText(scanner.nextLine() + "\n");
                }
            } catch (Exception e) {
                System.out.println("File not found");
            }
        });
    }

    private void setupReverseButton() {
        reverseButton.setOnAction(actionEvent -> {
            String allText = textArea.getText();
            textArea.setText("");
            for (String line : allText.split("\n")) {
                textArea.appendText(reverseString(line) + "\n");
            }
        });
    }

    static String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    private void setupSaveButton() {
        saveButton.setOnAction(actionEvent -> {
            try (PrintWriter writer = new PrintWriter("output.txt")) {
                writer.println(textArea.getText());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}