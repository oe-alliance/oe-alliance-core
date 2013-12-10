KV = "3.9.7"
SRCDATE = "20131210"

SRC_URI[md5sum] = "9ed012c82a7cc57749038b4a1159b8c6"
SRC_URI[sha256sum] = "ec6cb44acb92a03d0c40e9c1b2b4ae346add17b39d064fd4ac74cd820c77d271"

SRC_URI = "http://whitebox.host.sk/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
