SRCDATE = "20131102"

KV = "3.8.7"

SRC_URI[md5sum] = "72cf8b38436d65495ee96e6fdfcde16e"
SRC_URI[sha256sum] = "5b59624dd24674ee4fb5b4f883b833ea151b561326ef21e8866b9be78b00f5fc"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-gb800xxplus-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

