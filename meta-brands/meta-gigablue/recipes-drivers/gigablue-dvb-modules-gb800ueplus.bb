SRCDATE = "20140710"

KV = "3.12.1"

SRC_URI[md5sum] = "72013dca96c2a427dbfbbac1f2e98184"
SRC_URI[sha256sum] = "440d8fb645eb100559467111c7d96a5a72d9e8f11c70172a31feb015288d7d0a"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

