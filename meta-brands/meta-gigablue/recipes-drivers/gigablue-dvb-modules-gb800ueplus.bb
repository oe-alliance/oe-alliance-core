SRCDATE = "20140807"

KV = "3.14.2"

SRC_URI[md5sum] = "c228ed660b72677a4c40d7542909bbf9"
SRC_URI[sha256sum] = "f5cf4bb0c08588c42776d2201b82da89e2656bba2a9c4f102fec8c0f31628f2e"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

