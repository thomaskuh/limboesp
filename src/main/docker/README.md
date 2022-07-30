LimboESP, officially dockerized!
======
Your nice and simple OTA firmware update server for ESP8266 and ESP32 devices.

Startup (as deamon):

    docker run -d -p 8080:8080 -v DATA-DIR:/data limbomedia/limboesp

Startup (interactive):

    docker run -it -p 8080:8080 -v DATA-DIR:/data limbomedia/limboesp

Important:
The initial username and password is "admin". At least the password should be changed in DATA-DIR/limboesp.cfg which is created upon first startup.

## Links
* [LimboESP on GitHub](https://github.com/thomaskuh/limboesp)
* [LimboESP on Docker](https://hub.docker.com/r/limbomedia/limboesp)