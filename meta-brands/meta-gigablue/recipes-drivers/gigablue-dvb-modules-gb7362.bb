SRCDATE = "20170525"

KV = "4.8.3"

SRC_URI[md5sum] = "b05f9f88bae3ab8a6cbc8edae1070f86"
SRC_URI[sha256sum] = "a125d4adc1a8c977cde8387542351323cd2d57d8ad44af4d5ef0ab33219fb588"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
