KV = "3.9.7"
SRCDATE = "20131213"

SRC_URI[md5sum] = "dae1f3ee397f9ea0a766dfd53f611772"
SRC_URI[sha256sum] = "0c8005376475db7d5e950696e2981daa15e87e61583a3e3da033b86db5c3fd99"

SRC_URI = "http://whitebox.host.sk/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
