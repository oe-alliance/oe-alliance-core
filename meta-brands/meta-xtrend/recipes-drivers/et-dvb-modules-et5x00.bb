KV = "3.8.7"
SRCDATE = "20180412"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

RDEPENDS_${PN} += "et-fpupdate-${MACHINE}"

require et-dvb-modules.inc

SRC_URI[md5sum] = "4f0ad876a1c04ae51772376ae20bb925"
SRC_URI[sha256sum] = "72835eb22f8de8dc49f5e502d1be4f66f67378104c1215c1afa1b750cc813aa2"
