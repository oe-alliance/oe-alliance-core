SRCDATE = "20140729"

KV = "3.12.1"

SRC_URI[md5sum] = "7bc6317fd27153d249af50c700747a72"
SRC_URI[sha256sum] = "c20279ee1f5df882ce6d216ec07405f65b151d45d1ad62c38b79ac9fa0f7f2f5"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

