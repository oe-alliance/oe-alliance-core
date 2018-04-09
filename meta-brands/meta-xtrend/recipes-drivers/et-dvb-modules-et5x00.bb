KV = "3.8.7"
SRCDATE = "20180409"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

RDEPENDS_${PN} += "et-fpupdate-${MACHINE}"

require et-dvb-modules.inc

SRC_URI[md5sum] = "07a56634db4e05a109f0bcbcaa8587bc"
SRC_URI[sha256sum] = "d4290be941e9b5d103de8fa5b6b9655a5e21b90c20b739a5a666ca1212e85497"
