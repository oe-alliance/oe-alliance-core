KV = "3.6.0"
SRCDATE = "20140206"

SRC_URI[md5sum] = "deb55d4a9b98a85655adb88cae0664b4"
SRC_URI[sha256sum] = "6251361cf8034dc3fbd536c7acb0e9374d9d5cabb25e97b8d75754c5709f2774"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhdx"
RCONFLICTS = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
