SRCDATE = "20141002"

KV = "3.14.2"

SRC_URI[md5sum] = "bb53dc86050ba4237aec314594ca7f95"
SRC_URI[sha256sum] = "ae50f088c5f6a469b25a1398f03ce54d8a603903ee21949a1f2f69f63bba8229"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gbquadseries-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

