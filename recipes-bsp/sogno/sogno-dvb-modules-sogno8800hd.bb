KV = "3.9.7"
SRCDATE = "20131219"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "f53e4bbf6b91760af710e76880342ec0"
SRC_URI[sha256sum] = "70990ba790036332515f6196502a1fe1cec2ec06aab0ebd34c66d75b5a786912"

SRC_URI = "http://whitebox.host.sk/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
