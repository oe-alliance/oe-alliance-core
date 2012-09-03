SRCDATE = "20120901"

KV = "3.1.1"

SRC_URI[md5sum] = "2e0340773952b92be62564d157c4f68b"
SRC_URI[sha256sum] = "58c289ec51e66a18399116a09d846d53a08f143e3f12538f6228b4f3987692e3"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

