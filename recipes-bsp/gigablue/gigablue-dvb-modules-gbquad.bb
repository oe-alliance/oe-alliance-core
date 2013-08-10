SRCDATE = "20130809"

KV = "3.3.8-2.0"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-quad-drivers-${KV}-${SRCDATE}.zip"
SRC_URI[md5sum] = "9c71d14c16124538905cd55c67a69fcc"
SRC_URI[sha256sum] = "df54cfe8574de8e56897acadedc08c69ca2f7467f4b71af095e46a1a3efba60c"

require gigablue-dvb-modules.inc

