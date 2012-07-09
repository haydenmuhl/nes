package com.haydenmuhl.nes;

import java.util.logging.*;
import org.testng.Reporter;

public class TestNgReporterHandler extends java.util.logging.Handler {
    @Override
    public void close() {}
    
    @Override
    public void flush() {}
    
    @Override
    public void publish(LogRecord record) {
        if (record.getLevel().intValue() >= getLevel().intValue()) {
            Reporter.log(getFormatter().format(record));
        }
    }
}
