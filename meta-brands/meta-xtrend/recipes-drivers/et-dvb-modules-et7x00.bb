KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160527"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "8e381452ec11840d4eee95e6d1ca2bc3"
SRC_URI[sha256sum] = "1b483544d1393d32c7c0500f8ff78423d13f40d203684b30b5999381c69d685f"
