SRCDATE = "20140710"

KV = "3.3.8-2.0"

SRC_URI[md5sum] = "c7bf1674b079f388d93df0505891e92b"
SRC_URI[sha256sum] = "9c6fd14bb8104124cdded543d4e484f6ab88659a38e8d2ef99b5b09028a6cbeb"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gbquad-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

