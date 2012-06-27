SRCDATE = "20120604"

KV = "3.1.1"

SRC_URI[md5sum] = "785d297d577311713ab7ad21fa2b2045"
SRC_URI[sha256sum] = "e94dcd5f2127a406066b6c75f1d24c55b7c48304d1b77ada95afc4c271cf09d9"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

