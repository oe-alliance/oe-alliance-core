SRCDATE = "20141111"

KV = "3.14.2"

SRC_URI[md5sum] = "19ed4fbb462fe2ff486a4e892687af8c"
SRC_URI[sha256sum] = "391653667094ca931b2c0f6f024b006bd420695be3aeca4d2a4874bdb90f0349"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
