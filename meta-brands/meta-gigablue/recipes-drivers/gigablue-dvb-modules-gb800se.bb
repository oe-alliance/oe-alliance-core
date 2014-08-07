SRCDATE = "20140726"

KV = "3.1.1"

SRC_URI[md5sum] = "0e1275c8d203325a00929e35ce083694"
SRC_URI[sha256sum] = "eb0eae8fdba7fddb8994f1a6ca1787b561987dc4e3a7521b45750da8a1df38b2"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xx-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
