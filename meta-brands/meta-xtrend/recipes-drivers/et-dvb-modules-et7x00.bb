KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160812"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "a683dbf4fb81e3c0b7eb38f94453064b"
SRC_URI[sha256sum] = "2e0491c0b729c89a48dc0392cec97ac742892c56173d895b79b7f9dac1db8ee8"
