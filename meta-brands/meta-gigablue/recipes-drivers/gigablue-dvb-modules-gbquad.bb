SRCDATE = "20140731"

KV = "3.14.2"

SRC_URI[md5sum] = "5fef78e76fe49aee384d26293b6ba12d"
SRC_URI[sha256sum] = "ab8eb4189e7232cbccd66bcfd5b0377c10dbfb69d5225748d0113daf1d8405d2"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gbquad-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

