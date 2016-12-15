SRCDATE = "20161215"

KV = "4.0.1"

SRC_URI[md5sum] = "e2cd46bd343e4c9de3d320fc262dbabb"
SRC_URI[sha256sum] = "fd895616df1d6821c21405604717ad5f37e22ae7b1ae57e26f731cb26b1b379f"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
