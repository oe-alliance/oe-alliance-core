SRCDATE = "20130125"

KV = "3.1.1"

SRC_URI[md5sum] = "3024dbb0fbcfffec0f51c2886328a2f9"
SRC_URI[sha256sum] = "448c2088f26f2244a50785b459b01f158154665fef8e6218fb502586281d9d07"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

