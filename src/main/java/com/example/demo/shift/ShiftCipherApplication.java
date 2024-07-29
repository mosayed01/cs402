package com.example.demo.shift;

import com.example.demo.base.BaseCipherApplication;

public class ShiftCipherApplication extends BaseCipherApplication<Integer> {
    private static final ShiftCipherLogic cipher = new ShiftCipherLogic();

    public ShiftCipherApplication() {
        super(
                "Shift Cipher Application",
                cipher,
                cipher
        );
    }

    @Override
    protected void setKEY() {
        key = keyTextField.getText().isEmpty() ? 0 : Integer.parseInt(keyTextField.getText());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
