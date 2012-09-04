SRCDATE = "20120903"

KV = "3.1.1"

SRC_URI[md5sum] = "65832b1e117f49491d6c3d52cd02f1af"
SRC_URI[sha256sum] = "17530bb9bc53c764ffba4de8e4690cc9d9e4225c7d79a9373bebe7f0bb9619e2"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

