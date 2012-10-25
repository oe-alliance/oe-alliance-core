SRCDATE = "20121019"

KV = "2.6.37-2.8"

SRC_URI = "http://archiv.openmips.com/gigablue-quad-drivers-${KV}-${SRCDATE}.zip"
SRC_URI[md5sum] = "45d7874bdb20c2d8978d2222569fd554"
SRC_URI[sha256sum] = "49af7c5c584de613539e086e0bedcd2903090171397e862f3209804851739be3"

require gigablue-dvb-modules.inc

