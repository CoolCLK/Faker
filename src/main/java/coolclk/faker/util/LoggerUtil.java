package coolclk.faker.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.spi.AbstractLogger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class LoggerUtil {
    public static Logger getLogger() {
        return new AbstractLogger() {
            @Override
            protected boolean isEnabled(Level level, Marker marker, Message data, Throwable t) {
                return true;
            }

            @Override
            protected boolean isEnabled(Level level, Marker marker, Object data, Throwable t) {
                return true;
            }

            @Override
            protected boolean isEnabled(Level level, Marker marker, String data) {
                return true;
            }

            @Override
            protected boolean isEnabled(Level level, Marker marker, String data, Object... p1) {
                return true;
            }

            @Override
            protected boolean isEnabled(Level level, Marker marker, String data, Throwable t) {
                return true;
            }

            @Override
            public void log(Marker marker, String fqcn, Level level, Message data, Throwable t) {
                System.out.println("[Faker/" + getLevelDisplayName(level) + "] " + data.getFormattedMessage());
            }
        };
    }

    public static String getLevelDisplayName(Level level) {
        switch (level) {
            case INFO: {
                return level.name();
            }
            case WARN: {
                return level.name();
            }
            case DEBUG: {
                return level.name();
            }
            case ERROR: {
                return level.name();
            }
            case FATAL: {
                return level.name();
            }
            case TRACE: {
                return level.name();
            }
        }
        return level.name();
    }
}
