SRCDATE = "20130917"

KV = "3.8.7"

SRC_URI[md5sum] = "5f6387c27c1cd7fb9b1a4a021b24259d"
SRC_URI[sha256sum] = "3a701184658335ce0a39aa9775cef8f06cd09a20d5fa6c3d7e08ed194f45f5a3"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-gb800xxplus-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

