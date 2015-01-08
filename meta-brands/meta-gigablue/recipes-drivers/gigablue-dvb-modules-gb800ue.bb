SRCDATE = "20141217"

KV = "3.9.6"

SRC_URI[md5sum] = "9c0bfec1451d23ee50edb5bcf2c2b0c6"
SRC_URI[sha256sum] = "772cd8855313522c6c90f4a58047b413d2f44f674dd2e8ff78cb19f235b038b1"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xx-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
