package com.example.demo.ciphers.des;

import com.example.demo.base.BaseCipherApplication;
import com.example.demo.ciphers.des.logic.DESCipher;
import com.example.demo.ciphers.des.logic.utils.HexString;

public class DESCipherApplication extends BaseCipherApplication<HexString> {

    private static final DESCipher desCipher = new DESCipher();

    public DESCipherApplication(){
        super(
          "DES",
          desCipher,
          null
        );
    }

    @Override
    protected void setKEY() {
        key = new HexString(keyTextField.getText());
    }

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> showErrorDialog(throwable));
        launch(args);
    }
}