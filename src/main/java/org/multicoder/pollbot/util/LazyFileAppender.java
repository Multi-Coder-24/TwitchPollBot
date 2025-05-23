package org.multicoder.pollbot.util;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;

import java.io.File;
public class LazyFileAppender extends FileAppender<ILoggingEvent> {

    private boolean fileInitialized = false;
    public File LogFile;

    @Override
    protected void subAppend(ILoggingEvent event) {
        if (!fileInitialized)
        {
            LogFile = new File(getFile());
            if (event.getMessage() != null && !event.getMessage().trim().isEmpty()) {
                setFile(getFile()); // Initialize the file
                LogFile = new File(getFile());
                super.start();
                fileInitialized = true;
            } else {
                return; // Skip logging if the message is empty
            }
        }
        super.subAppend(event);
    }
}

