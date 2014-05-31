SRCDATE = "20140523"

KV = "3.12.1"

SRC_URI[md5sum] = "71f30fc4e5d7551823c05d9c2c72b837"
SRC_URI[sha256sum] = "d3cfe97a9ede49cc357d3bb0463d7321e29cd3b7f2ac23ae08a9f16a1d896953"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

