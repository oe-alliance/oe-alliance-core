KV = "3.8.7"
SRCDATE = "20180412"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

RDEPENDS_${PN} += "et-fpupdate-${MACHINE}"

require et-dvb-modules.inc

SRC_URI[md5sum] = "2ff542f55d8d2f80f6610538b7bb1465"
SRC_URI[sha256sum] = "9f323285449cc14ccd54863b85df41161d5e1afbe552ba37e41cc68aca5a38fd"
