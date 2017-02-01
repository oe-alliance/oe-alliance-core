SRCDATE = "20170118"

KV = "4.8.3"

SRC_URI[md5sum] = "339951fa2f31330186820d5c8f269494"
SRC_URI[sha256sum] = "0689b94e798b44b386f820a89e85f45924feaf49720750d6050ea6b462d1a567"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
