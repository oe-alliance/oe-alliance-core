KV = "3.9.7"
SRCDATE = "20131203"

SRC_URI[md5sum] = "568afed87ca49d975469c9b4fd2bad3c"
SRC_URI[sha256sum] = "a392f9e8947726dad1c491fd67666645eeaf188cb39cd6d77d3da272d2a1de56"

SRC_URI = "http://whitebox.host.sk/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
