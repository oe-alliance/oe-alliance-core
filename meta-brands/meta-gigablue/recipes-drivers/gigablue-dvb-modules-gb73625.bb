SRCDATE = "20170302"

KV = "4.8.3"

SRC_URI[md5sum] = "cb8f54a3bca029628a63762ff902ca43"
SRC_URI[sha256sum] = "e1aea018233c6411b13b8e299b9bb414a56522f746abd53b0429088daee5e094"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM73625-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
