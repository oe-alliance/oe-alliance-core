PACKAGE_ARCH = "${MACHINE_ARCH}"

KV = "3.14.2"
SRCDATE = "20140917"

SRC_URI[md5sum] = "02af1ad5cd5657527342c44623abe84c"
SRC_URI[sha256sum] = "6fa2aac3ba634ce1f792d66449422a0e0d858aec133e45d7696bde1dc46417ba"

SRC_URI = "http://code-ini.com/software/drivers/ini-422-drivers-${KV}-${SRCDATE}.zip"

require ini-dvb-modules.inc

