SRCDATE = "20120914"

KV = "3.1.1"

SRC_URI[md5sum] = "22e656a76511f7f29cca564d722959c8"
SRC_URI[sha256sum] = "f91a365d904ec8cf896e7cf9420a92facda1489913f6181e098a1e08c079b091"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

