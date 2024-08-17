package com.example.demo.base;

import com.example.demo.utils.Utils;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public abstract class BaseCipherApplication<KEY> extends Application {
    protected final String title;
    protected final boolean isEditableKeyTextField;
    protected final boolean isAttackable;
    protected ICipher<KEY> cipher;
    protected IKnownKeyFileReader<KEY> keyFileReader;
    protected IAttackable attackable;

    public BaseCipherApplication(String title, IKnownKeyFileReader<KEY> keyFileReader, ICipher<KEY> cipher, boolean isEditableKeyTextField, boolean isAttackable, IAttackable attackable) {
        this.title = title;
        this.keyFileReader = keyFileReader;
        this.isEditableKeyTextField = isEditableKeyTextField;
        this.cipher = cipher;
        this.isAttackable = isAttackable;
        this.attackable = attackable;
    }

    public BaseCipherApplication(String title, ICipher<KEY> cipher, IAttackable attackable) {
        this.title = title;
        this.isEditableKeyTextField = true;
        this.cipher = cipher;
        this.isAttackable = attackable != null;
        this.attackable = attackable;
    }

    protected KEY key;

    protected static final Label inputLabel = new Label("Input:");
    protected static final TextArea inputTextArea = new TextArea();
    protected static final Label keyLabel = new Label("Key:");
    protected static final TextArea keyTextField = new TextArea();
    protected static final Button encryptButton = new Button("Encrypt");
    protected static final Button decryptButton = new Button("Decrypt");
    protected static final Button attackButton = new Button("Attack");
    protected static final Button readKeyButton = new Button("Read key");
    protected static final Button readInputButton = new Button("Read input");
    protected static final Button writeOutputButton = new Button("Write output");
    protected static final Label outputLabel = new Label("Output:");
    protected static final TextArea outputTextArea = new TextArea();
    protected static final GridPane gridPane = new GridPane();


    protected void setupUI() {
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(inputLabel, 0, 0);
        gridPane.add(inputTextArea, 1, 0, 2, 1);
        gridPane.add(keyLabel, 0, 1);
        gridPane.add(keyTextField, 1, 1, 2, 1);
        gridPane.add(encryptButton, 0, 2);
        gridPane.add(decryptButton, 1, 2);
        if (isAttackable) gridPane.add(attackButton, 2, 2);
        if (keyFileReader != null) gridPane.add(readKeyButton, 0, 3);
        gridPane.add(readInputButton, 1, 3);
        gridPane.add(writeOutputButton, 2, 3);
        gridPane.add(outputLabel, 0, 4);
        gridPane.add(outputTextArea, 1, 4, 2, 1);
    }

    protected void setupActions(Stage stage) {
        keyTextField.setEditable(isEditableKeyTextField);
        outputTextArea.setEditable(false);

        if (keyFileReader != null) readKeyButton.setOnMouseClicked(e -> {
            key = keyFileReader.readKeyFile(stage);
            if (key == null) {
                keyTextField.setText("Key not loaded.");
                return;
            }
            keyTextField.setText(key.toString());
        });

        readInputButton.setOnMouseClicked(e -> {
            String input = Utils.readTextFromFile(stage);
            if (input != null) {
                inputTextArea.setText(input);
            }
        });

        writeOutputButton.setOnMouseClicked(e -> Utils.writeTextToFile(stage, outputTextArea.getText()));

        encryptButton.setOnAction(e -> {
            if (key == null && keyFileReader != null) {
                keyTextField.setText("Key not loaded. Please load the key first.");
                return;
            }

            setKEY();
            String input = inputTextArea.getText();
            String output = cipher.encrypt(input, key);
            outputTextArea.setText(output);
        });

        decryptButton.setOnAction(e -> {
            if (key == null && keyFileReader != null) {
                keyTextField.setText("Key not loaded. Please load the key first.");
                return;
            }
            setKEY();
            String input = inputTextArea.getText();
            String output = cipher.decrypt(input, key);
            outputTextArea.setText(output);
        });

        if (isAttackable) {
            attackButton.setOnAction(e -> {
                String input = inputTextArea.getText();
                String output = attackable.attack(input);
                outputTextArea.setText(output);
            });
        }
    }

    @Override
    public void start(Stage stage) {
        setupUI();
        setupActions(stage);

        otherSetup(stage);

        Scene scene = new Scene(gridPane, 400, 300);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    protected void otherSetup(Stage stage) {
        // override this method to add additional setup
    }

    protected void setKEY() {
        // override this method to set the key
    }

    protected static void showErrorDialog(Throwable throwable) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(throwable.getClass().getSimpleName());
        alert.setHeaderText(null);
        alert.setContentText(throwable.getMessage());
        alert.showAndWait();
    }
}