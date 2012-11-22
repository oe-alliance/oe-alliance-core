SRCDATE = "20121121"

KV = "2.6.37-2.8"

SRC_URI = "http://archiv.openmips.com/gigablue-quad-drivers-${KV}-${SRCDATE}.zip"
SRC_URI[md5sum] = "4bb0ed2d63eaaccdfd515ea57937bfbd"
SRC_URI[sha256sum] = "f1611bced73173b99fa0a9ff669033e633baef6b97a3d74a441bd1577e8796c2"

require gigablue-dvb-modules.inc

