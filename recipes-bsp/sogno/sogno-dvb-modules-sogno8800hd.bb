KV = "3.9.7"
SRCDATE = "20131129"

SRC_URI[md5sum] = "56ff4335620899c67b011e0f3bdc654f"
SRC_URI[sha256sum] = "d4541067f51845c6fbaa95a711ab2d635aa087d4e3111b2920fddc21850d4050"

SRC_URI = "http://whitebox.host.sk/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
