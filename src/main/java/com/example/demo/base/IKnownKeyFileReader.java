package com.example.demo.base;

import javafx.stage.Window;

public interface IKnownKeyFileReader<O> {
    O readKeyFile(Window window);
}
