SRCDATE = "20141120"

KV = "3.14.2"

SRC_URI[md5sum] = "f4d4ab91c628f20d24dc5ce3a09f08e5"
SRC_URI[sha256sum] = "d22394da833fb37322c2555f12f05f1ad283a525c27d670e45e16cfffb9d1bb7"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gbquadseries-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
