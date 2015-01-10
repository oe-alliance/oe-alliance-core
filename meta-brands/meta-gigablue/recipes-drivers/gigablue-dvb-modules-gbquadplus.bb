SRCDATE = "20150105"

KV = "3.14.2"

SRC_URI[md5sum] = "2a35f31786ec1d00a8ec56bb7e5a6290"
SRC_URI[sha256sum] = "87cc62d04d734962deb99c9c478ab1e92e4278cab262dfc7bd334c53b7b03abf"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gbquadseries-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
