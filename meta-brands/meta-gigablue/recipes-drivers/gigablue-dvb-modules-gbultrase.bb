SRCDATE = "20160301"

KV = "4.0.1"

SRC_URI[md5sum] = "a8510cea50ebcf205077925747e75acc"
SRC_URI[sha256sum] = "1b583ef433ce6f5ac8c521b90f49bad3920fb5365129e96419d7dbced25763c2"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc