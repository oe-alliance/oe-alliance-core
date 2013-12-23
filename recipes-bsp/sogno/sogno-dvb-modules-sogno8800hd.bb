KV = "3.9.7"
SRCDATE = "20131224"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "416519c12dd56cf7254ac347ff58ea17"
SRC_URI[sha256sum] = "b307d2b2f51293a8401e6ea7140767cad0a01ec136a947a2e3cf4469d759ac64"

SRC_URI = "http://whitebox.host.sk/bcm/driver/sogno8800hd-drivers-${KV}-${SRCDATE}.zip"

require sogno-dvb-modules.inc
