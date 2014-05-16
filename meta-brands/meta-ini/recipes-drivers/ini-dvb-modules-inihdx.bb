KV = "3.6.0"
SRCDATE = "20140505"

SRC_URI[md5sum] = "d52d1f1dce90b3d41b28b2e062e6653b"
SRC_URI[sha256sum] = "69d287040c536d9d814e021729270ee05556f24edf4513aeab5e31144dc2ccb7"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES_${PN} = "venton-dvb-modules-ventonhdx"
RCONFLICTS_${PN} = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
