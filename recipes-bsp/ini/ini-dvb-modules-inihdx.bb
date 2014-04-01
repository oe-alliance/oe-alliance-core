KV = "3.6.0"
SRCDATE = "20140401"

SRC_URI[md5sum] = "4651212816896cbab008588f189c413f"
SRC_URI[sha256sum] = "e903ab37d50cf9596281d044b90c102d9bcc4343f4b624e017bfa306e2219951"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES_${PN} = "venton-dvb-modules-ventonhdx"
RCONFLICTS_${PN} = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
