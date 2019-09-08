# LimboESP

LimboESP is your nice and simple OTA firmware update server for ESP8266 and ESP32 devices.

## Features
* Handles ESP8266 and ESP32 HTTP OTA firmware update requests.
* Web interface for device and app management.
* Upload your binaries (generated via arduino, platform.io, toolchain).
* LimboESP keeps track of always having the latest version on your devices.
* Runs on Docker, Linux, Windows and other Java capable OS.

## Setup and run (One-file-server with Java)
```
# Java 8+ is required, so depending on your distribution type something like this:
sudo apt-get install default-jre-headless
pacman -S jre-openjdk-headless

# Get it
wget -O limboesp.jar https://repo.kuhlins.org/artifactory/public/net/limbomedia/limboesp/0.1/limboesp-0.1.jar

# Run it
# -Ddir specifies where to store config and data.
java -Ddir=/path/for/data -jar limboesp.jar

# Visit admin interface: http://YOUR-SERVER:8080
```

## Setup and run (Docker)
```
docker run -d -p 8080:8080 -v DATA-DIR:/data limbomedia/limboesp
```

## Configuration
The default configuration file `[DATA_DIR]/limboesp.cfg` is created upon the first start. Edit to set:
* HTTP port
* Username and password
* Forward header if you run it behind a reverse proxy

## Setup your ESP devices
LimboESP is built to work with the default ESP SDK OTA facility, so there's no need for additional libraries. Check official docs for [ESP8266](https://arduino-esp8266.readthedocs.io/en/latest/ota_updates/readme.html#http-server) or [ESP32](https://docs.espressif.com/projects/esp-idf/en/latest/api-reference/system/ota.html).

As an example, here's my code called from time to time. For simplicity i merged header and code:
```
#if defined(ESP8266)
#include <ESP8266httpUpdate.h>
t_httpUpdate_return ret = ESPhttpUpdate.update(clientWifi, "http://LIMBOESP_SERVER:8080/update/esp8266", "optional info string");
#endif

#if defined(ESP32)
#include <HTTPUpdate.h>
t_httpUpdate_return ret = httpUpdate.update(clientWifi, "http://LIMBOESP_SERVER:8080/update/esp32", "optional info string");
#endif
```

Your device registers itself upon the first request. Visit the webinterface to approve the device, assign an app and check device information.

## Links
* [LimboESP on GitHub](https://github.com/thomaskuh/limboesp)
* [LimboESP on Docker](https://hub.docker.com/r/limbomedia/limboesp)
