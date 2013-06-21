SRCDATE = "20130621"

KV = "3.1.1"

SRC_URI[md5sum] = "671368209765f19524b5c1437a5c356b"
SRC_URI[sha256sum] = "23f9b7286b31e5dda5a067e58ef79a841465eb1d12b4ffe26d586c6666412e07"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

