KV = "3.9.7"
SRCDATE = "20131204"

SRC_URI[md5sum] = "9b433e308768a9216c1e9c3ade722214"
SRC_URI[sha256sum] = "ecf411aff3f7619c01b9f6649fb53fa45212784dfc1654d9a51b0a2ac7550740"

SRC_URI = "http://whitebox.host.sk/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
