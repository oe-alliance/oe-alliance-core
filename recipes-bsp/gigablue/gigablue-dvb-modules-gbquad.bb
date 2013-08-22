SRCDATE = "20130820"

KV = "3.3.8-2.0"

SRC_URI = "http://archiv.openmips.com/gigablue-quad-drivers-${KV}-${SRCDATE}.zip"
SRC_URI[md5sum] = "4e2fe54f6ffc8df03ae069739604b42a"
SRC_URI[sha256sum] = "453576e5adecace5c5cc71d19bfc4e0f435964d92003a133f400fddae666c835"

require gigablue-dvb-modules.inc

