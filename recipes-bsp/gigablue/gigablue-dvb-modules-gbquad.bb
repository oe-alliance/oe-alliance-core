SRCDATE = "20120604"

KV = "2.6.37"

SRC_URI = "http://archiv.openmips.com/gigablue-quad-drivers-${KV}-${SRCDATE}.tar.gz"

SRC_URI[md5sum] = "ee68cdd19ba276337c26e824faa6e27a"
SRC_URI[sha256sum] = "ef1b9a435a19bd0b6247336a46af2e11352bc3b7678a63ad66e8f896274f049e"

require gigablue-dvb-modules.inc

