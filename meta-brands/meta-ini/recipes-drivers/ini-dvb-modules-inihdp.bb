KV = "3.14.2"
SRCDATE = "20140522"

SRC_URI[md5sum] = "5dd6fcd8f0d72100e2ca50df5a3e601c"
SRC_URI[sha256sum] = "0c3d2b759fe2e83f33cc18069788e7f00fa07398728b43303a67e2d780e519c8"

SRC_URI = "http://code-ini.com/software/beta/drv/hdp/ini-hdp-drivers-${KV}-${SRCDATE}.zip"

require ini-dvb-modules.inc
