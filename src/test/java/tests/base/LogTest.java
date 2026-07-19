package tests.base;

import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

@Log4j2
public class LogTest {

    @Test
    public void checkLogs() {
        log.fatal("fatal error");
        log.error("error");
        log.warn("warning");
        log.info("info");
        log.debug("debug");
        log.trace("trace");
    }
}
