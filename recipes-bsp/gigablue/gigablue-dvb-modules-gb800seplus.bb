SRCDATE = "20131103"

KV = "3.8.7"

SRC_URI[md5sum] = "8336f8db14745dbcdeb28f7782341448"
SRC_URI[sha256sum] = "fb9c7dbc6b0158cf4edfde061e13fa05a1dba67e85211d08dc447b1ba5b3fced"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-gb800xxplus-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

