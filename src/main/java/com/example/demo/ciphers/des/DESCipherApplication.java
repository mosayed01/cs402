package com.example.demo.ciphers.des;

import com.example.demo.base.BaseCipherApplication;
import com.example.demo.ciphers.des.logic.DESCipher;
import com.example.demo.ciphers.des.logic.utils.HexString;
import com.example.demo.ciphers.des.logic.utils.Utils;

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
        String hexKey = Utils.convertStringToHex64Bit(keyTextField.getText(), true);
        key = new HexString(hexKey);
    }

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> showErrorDialog(throwable));
        launch(args);
    }
}