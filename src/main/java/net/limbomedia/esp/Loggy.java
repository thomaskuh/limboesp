package net.limbomedia.esp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Loggy {

    public static final Logger CORE = LoggerFactory.getLogger("limboesp.core");
    public static final Logger WEB = LoggerFactory.getLogger("limboesp.web");
    public static final Logger UPDATE_APP = LoggerFactory.getLogger("limboesp.update.app");
    public static final Logger UPDATE_DEV = LoggerFactory.getLogger("limboesp.update.dev");
}
