KV = "3.9.7"
SRCDATE = "20131225"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "d582ea8b77d1f0927c7b9a63f1668c25"
SRC_URI[sha256sum] = "0f67ad5edbd2c461961327ca5f0a11fc3e6b54b7cf4eb90b1c648b90df918c4f"

SRC_URI = "http://whitebox.host.sk/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
