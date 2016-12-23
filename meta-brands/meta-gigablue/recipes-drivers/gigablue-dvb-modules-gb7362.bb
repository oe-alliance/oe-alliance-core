SRCDATE = "20161219"

KV = "4.0.1"

SRC_URI[md5sum] = "8712d93ecbbdfe784e8273ef084f1d48"
SRC_URI[sha256sum] = "1518f25367599ad8d68d1bcc6f4dca12601cc8ba5e016cd5f789babc85ccd7fc"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
