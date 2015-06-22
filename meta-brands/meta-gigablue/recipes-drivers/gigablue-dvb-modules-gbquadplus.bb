SRCDATE = "20150607"

KV = "3.14.2"

SRC_URI[md5sum] = "d286123dc27f1588888db163c3018d53"
SRC_URI[sha256sum] = "a460fb58f7acf2e4b5628d97a279f8f1c84f45ff5748537797fd8658bc967535"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
