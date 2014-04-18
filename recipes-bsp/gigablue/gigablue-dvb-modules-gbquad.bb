SRCDATE = "20140417"

KV = "3.3.8-2.0"

SRC_URI[md5sum] = "58d64cf6125fa255fe69be7fde55df2e"
SRC_URI[sha256sum] = "bb2cb5d1265c6c833c9eaffc5d809d2ce6dd1bee1372037f634bf097459b2040"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gbquad-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

