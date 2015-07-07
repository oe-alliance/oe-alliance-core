SRCDATE = "20150703"

KV = "3.14.2"

SRC_URI[md5sum] = "ead7b04d5d42b2e312ea9d8b5007f447"
SRC_URI[sha256sum] = "1d54bebf38fb1b22f1f14135ac6b05ffebd0d16938b226e48283a62556948c76"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
