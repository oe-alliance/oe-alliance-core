SRCDATE = "20150327"

KV = "3.14.2"

SRC_URI[md5sum] = "eb30eb02675266b7cdcca25259d5b73a"
SRC_URI[sha256sum] = "a2441f9e5f1458887cb686b49d823eeaf3b23fee309f471e51c36d933a1410f8"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gbquadseries-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
