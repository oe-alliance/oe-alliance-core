SRCDATE = "20121019"

KV = "3.1.1"

SRC_URI[md5sum] = "5b41d19bec01cd8098218583484110c9"
SRC_URI[sha256sum] = "42d8bd500f4fad3dc168b5cd3fe2fffa42201e1c40ae8befd3f1bf337b2393cf"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

