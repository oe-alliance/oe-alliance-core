KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20161004"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "52d1104b28dbf07019ef0f2cdc88d16a"
SRC_URI[sha256sum] = "cad36d1d29e79c5ec5e9130140d2f2d3262c6bb00be241084b9ce1c008607b17"
