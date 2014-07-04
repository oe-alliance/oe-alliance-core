SRCDATE = "20140704"

KV = "3.3.8-2.0"

SRC_URI[md5sum] = "73173666a27ff576c333339f95a7a7a1"
SRC_URI[sha256sum] = "271eaeec0452bd3b7e5b48048ffaec976313dbc7d5d06c1e12f31882efaa7a32"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gbquadplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

