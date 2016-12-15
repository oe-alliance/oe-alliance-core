SRCDATE = "20161215"

KV = "4.0.1"

SRC_URI[md5sum] = "7c126470a8101ad2cc77eda8bde96eb8"
SRC_URI[sha256sum] = "144d8e3bf67a7ef3c76346bb0a44fd7fe17868d75ff9865fa50c159d7c155c25"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}a.zip"

require gigablue-dvb-modules.inc
