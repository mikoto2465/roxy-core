package net.mikoto.roxy.core.scanner;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

@Log4j2
public abstract class FileHandler {
    private FileHandler next;

    public void setNext(FileHandler next) {
        this.next = next;
    }

    public final void handle(File file) throws IOException {
        doHandle(file);
        if (next != null) {
            next.handle(file);
        } else {
            scanned(file);
        }
    }

    protected abstract void doHandle(File file) throws IOException;

    protected void scanned(@NotNull File file) {
        log.info("[Roxy] Resolved file -> " + file.getName());
    }
}
