package com.ab.aplikacje_biznesowe;

import javafx.scene.text.Text;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MyHandler extends Handler {
    private Text loggerOut;
    public MyHandler(Text loggerOut) {
        this.loggerOut = loggerOut;
    }
    @Override
    public void publish(LogRecord record) {
        loggerOut.setText(record.getLevel()+": "+record.getMessage());

    }
    @Override
    public void flush() {
        loggerOut.setText("");
    }
    @Override
    public void close() throws SecurityException {
        loggerOut.setText("");
    }
}
