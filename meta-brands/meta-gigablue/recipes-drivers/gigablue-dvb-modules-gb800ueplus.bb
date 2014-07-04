SRCDATE = "20140704"

KV = "3.12.1"

SRC_URI[md5sum] = "da9c21edd5ca816f4096116d2862b15a"
SRC_URI[sha256sum] = "00060325d7095abd49c15cb80b38213452b4e4f04daea3e24a54440c31ab658c"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

