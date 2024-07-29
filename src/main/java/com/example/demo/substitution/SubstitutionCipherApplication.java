package com.example.demo.substitution;

import com.example.demo.Utils;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Map;

public class SubstitutionCipherApplication extends Application {

    // region logic
    private static final SubstitutionICipherLogic cipher = new SubstitutionICipherLogic();
    private static Map<Character, Character> key;
    // endregion

    // region UI
    private static final Label inputLabel = new Label("Input:");
    private static final TextArea inputTextArea = new TextArea();
    private static final Label keyLabel = new Label("Key:");
    private static final TextArea keyTextField = new TextArea();
    private static final Button encryptButton = new Button("Encrypt");
    private static final Button decryptButton = new Button("Decrypt");
    private static final Button attackButton = new Button("Attack");
    private static final Button readKeyButton = new Button("Read key");
    private static final Button readInputButton = new Button("Read input");
    private static final Button writeOutputButton = new Button("Write output");
    private static final Label outputLabel = new Label("Output:");
    private static final TextArea outputTextArea = new TextArea();
    private static final GridPane gridPane = new GridPane();

    // endregion

    @Override
    public void start(Stage stage) {
        keyTextField.setEditable(false);
        outputTextArea.setEditable(false);

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(inputLabel, 0, 0);
        gridPane.add(inputTextArea, 1, 0, 2, 1);
        gridPane.add(keyLabel, 0, 1);
        gridPane.add(keyTextField, 1, 1, 2, 1);
        gridPane.add(encryptButton, 0, 2);
        gridPane.add(decryptButton, 1, 2);
        gridPane.add(attackButton, 2, 2);
        gridPane.add(readKeyButton, 0, 3);
        gridPane.add(readInputButton, 1, 3);
        gridPane.add(writeOutputButton, 2, 3);
        gridPane.add(outputLabel, 0, 4);
        gridPane.add(outputTextArea, 1, 4, 2, 1);

        readKeyButton.setOnMouseClicked(e -> {
            key = KeyFileReader.readKeyFile(stage);
            assert key != null;
            keyTextField.setText(key.toString());
        });

        readInputButton.setOnMouseClicked(e -> {
            String input = Utils.readTextFromFile(stage);
            if (input != null) {
                inputTextArea.setText(input);
            }
        });

        writeOutputButton.setOnMouseClicked(e ->
                Utils.writeTextToFile(stage, outputTextArea.getText())
        );

        encryptButton.setOnAction(e -> {
            if (key == null) {
                keyTextField.setText(
                        "Key not loaded. Please load the key first."
                );
                return;
            }

            String input = inputTextArea.getText();
            String output = cipher.encrypt(input, key);
            outputTextArea.setText(output);
        });

        decryptButton.setOnAction(e -> {
            if (key == null) {
                keyTextField.setText(
                        "Key not loaded. Please load the key first."
                );
                return;
            }

            String input = inputTextArea.getText();
            String output = cipher.decrypt(input, key);
            outputTextArea.setText(output);
        });

        attackButton.setOnAction(e -> {
            String input = inputTextArea.getText();
            String output = cipher.attack(input);
            outputTextArea.setText(output);
        });

        Scene scene = new Scene(gridPane, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Substitution Cipher Application");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
