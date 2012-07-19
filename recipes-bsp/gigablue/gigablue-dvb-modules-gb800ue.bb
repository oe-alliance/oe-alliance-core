SRCDATE = "20120713"

KV = "3.1.1"

SRC_URI[md5sum] = "8aa72d5b56b2720084807dda8a1650dd"
SRC_URI[sha256sum] = "a458e76897a059c5cbe9bdfe9ab7dd7d3d76a71897267a4df05c835e5af7567b"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

