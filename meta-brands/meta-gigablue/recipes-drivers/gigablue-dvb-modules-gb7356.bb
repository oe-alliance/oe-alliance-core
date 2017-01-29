SRCDATE = "20170118"

KV = "4.8.3"

SRC_URI[md5sum] = "8ab1a06c2deacb790d2db6f8fb499c5f"
SRC_URI[sha256sum] = "d966c5490597006e037a3ac0ef4951d2279d739408465f2d47e98b933ccbfc89"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
