package net.limbomedia.esp;

import java.io.IOException;
import java.nio.file.Path;
import org.kuhlins.lib.utils.cfg.Configurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LimboEsp {

    public static void main(String[] args) throws IOException {
        Path pathDirMain = Configurator.resolveDirectory("dir");
        Path pathFileCfg = pathDirMain.resolve("limboesp.cfg");
        Path pathFileLog = pathDirMain.resolve("limboesp.log");
        Path pathFileDb = pathDirMain.resolve("db");

        System.setProperty("log.file", pathFileLog.toString());
        System.setProperty("db.file", pathFileDb.toString());

        Configurator.createFileIfNotExisting(
                pathFileCfg, () -> LimboEsp.class.getResourceAsStream("/limboesp-template.cfg"));
        Configurator.readSystemPropertiesFromPath(pathFileCfg);
        Configurator.readSystemPropertiesFromClasspath("/limboesp-default.cfg");

        Loggy.CORE.info("Kickin up that thang...");
        Loggy.CORE.info("Data directory: {}", pathDirMain);
        Loggy.CORE.info("Config file   : {}.", pathFileCfg);

        SpringApplication.run(LimboEsp.class, args);

        Loggy.CORE.info("Startup complete. Ready to rumble!");
    }
}
