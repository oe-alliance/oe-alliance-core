SRCDATE = "20151214"

KV = "4.0.1"

SRC_URI[md5sum] = "ad3c05330df48c7eb0a3758bc70955c7"
SRC_URI[sha256sum] = "4317bc80b2d5404f4af932850cda9585e228b38da668133a8ebcf7fe9bbd5251"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
