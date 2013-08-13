SRCDATE = "20130813"

KV = "3.1.1"

SRC_URI[md5sum] = "2d10fce34f8c44eccbf451558f8068e4"
SRC_URI[sha256sum] = "b086e2e102552853f4f0630aa9f56444691c0b6a125ba929e3d43d9bde225b28"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

