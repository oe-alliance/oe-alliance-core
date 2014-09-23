PACKAGE_ARCH = "${MACHINE_ARCH}"

KV = "3.14.2"
SRCDATE = "20140923"

SRC_URI[md5sum] = "224d52667be70374f9929833e9736fec"
SRC_URI[sha256sum] = "321e1e96852ea89dece6327eec1fa9aa9b823475eb26d76702132e1e8099a83f"

SRC_URI = "http://code-ini.com/software/drivers/ini-422-drivers-${KV}-${SRCDATE}.zip"

require ini-dvb-modules.inc

