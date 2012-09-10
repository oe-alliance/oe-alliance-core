SRCDATE = "20120910"

KV = "3.1.1"

SRC_URI[md5sum] = "d8590181f084718aa50a177ac9a94997"
SRC_URI[sha256sum] = "d72d41cfd70d345cf60abddcd7176f20fdc79b91d66ebfa4cfaf6c9f1b65c825"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

