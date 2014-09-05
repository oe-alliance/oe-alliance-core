SRCDATE = "20140903"

KV = "3.14.2"

SRC_URI[md5sum] = "7c249dddd8e0fcfb02d83f2f870bbc50"
SRC_URI[sha256sum] = "b6322d4147c8d19c4ada638c7a1ab60892a695bf9eaccd80c67e3ecfbd136970"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

