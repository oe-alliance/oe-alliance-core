SRCDATE = "20170304"

KV = "4.8.3"

SRC_URI[md5sum] = "dd17ede376016c9a839d663291b3857d"
SRC_URI[sha256sum] = "572e6a1ef4e11467bbc99a0549c42a229778e40dc342b92ee3ba2d71235f1b72"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
