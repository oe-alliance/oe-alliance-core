SRCDATE = "20161215"

KV = "4.0.1"

SRC_URI[md5sum] = "f683ae5e18af66dcf456e4bdd148f6a4"
SRC_URI[sha256sum] = "cf363a7742f8421f51c7f7c2e54ca79307d174e0ff9cebba178ecb6a61a595cc"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
