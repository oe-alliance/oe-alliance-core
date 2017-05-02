SRCDATE = "20170502"

KV = "4.8.3"

SRC_URI[md5sum] = "5938ffb5ea9abbc0ebc55c501ede5f1d"
SRC_URI[sha256sum] = "7cf8d8a63c07b40a8ba2104182e56d75201abad8f874207f54ad1579cd7f9478"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
