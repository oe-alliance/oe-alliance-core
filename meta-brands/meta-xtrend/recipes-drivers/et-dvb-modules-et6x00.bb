KV = "3.8.7"
SRCDATE = "20180409"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

RDEPENDS_${PN} += "et-fpupdate-${MACHINE}"

require et-dvb-modules.inc

SRC_URI[md5sum] = "118c698c038c250f6a45f35abb5740fc"
SRC_URI[sha256sum] = "7e8b0a5c91f5bc71bd60dfb3f0d1f2829feff98df65c0a179ca3d01f99c7e355"
