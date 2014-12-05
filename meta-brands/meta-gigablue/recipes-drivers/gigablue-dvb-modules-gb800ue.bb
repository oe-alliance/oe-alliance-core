SRCDATE = "20141111"

KV = "3.9.6"

SRC_URI[md5sum] = "a7d4e802a7081590a36e4d75ea9513b2"
SRC_URI[sha256sum] = "8e1481f11c1b883e02ef5e968a8b3015e47c3a3dafd3d76dd4f71ecb27d4db93"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xx-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
