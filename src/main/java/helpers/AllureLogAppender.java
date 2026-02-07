package helpers;

import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.Level;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class AllureLogAppender extends AppenderBase<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent event) {

        // אל תדווח אם אין טסט פעיל
        if (!Allure.getLifecycle().getCurrentTestCase().isPresent()) {
            return;
        }

        // סינון – לא להציף את הדוח
        if (!event.getLevel().isGreaterOrEqual(Level.INFO)) {
            return;
        }

        String message = event.getFormattedMessage();

        Status status = mapStatus(event.getLevel());

        Allure.step(
                event.getLevel() + " - " + message,
                () -> {
                    if (status != Status.PASSED) {
                        Allure.getLifecycle().updateStep(s -> s.setStatus(status));
                    }
                }
        );
    }

    private Status mapStatus(Level level) {
        if (level.isGreaterOrEqual(Level.ERROR)) return Status.FAILED;
        if (level.isGreaterOrEqual(Level.WARN)) return Status.BROKEN;
        return Status.PASSED;
    }
}