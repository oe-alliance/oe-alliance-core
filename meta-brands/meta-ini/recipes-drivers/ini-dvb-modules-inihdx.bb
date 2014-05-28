KV = "3.6.0"
SRCDATE = "20140522"

SRC_URI[md5sum] = "aa6e16c08201ae8019465e811fe0aa7a"
SRC_URI[sha256sum] = "a9793f34f3ddcfdf3b463a40a74f8997eb880576012a2db7528859adcb1f2da1"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES_${PN} = "venton-dvb-modules-ventonhdx"
RCONFLICTS_${PN} = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
