SRCDATE = "20120830"

KV = "3.1.1"

SRC_URI[md5sum] = "73c90495112159ce14d91d195d1bedf3"
SRC_URI[sha256sum] = "4741c4695d25aa4fcc74a214ccba56a70f918fbcca0b2172cc15c4fc75af48cc"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

