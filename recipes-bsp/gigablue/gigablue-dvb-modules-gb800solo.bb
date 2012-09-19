SRCDATE = "20120918"

KV = "3.1.1"

SRC_URI[md5sum] = "dd45cee2490ade95e40ccec9e4267ac5"
SRC_URI[sha256sum] = "b21a09a017f859989ace2f9eafac83315099f0e97872535aeab8cbf0e07e4f54"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

