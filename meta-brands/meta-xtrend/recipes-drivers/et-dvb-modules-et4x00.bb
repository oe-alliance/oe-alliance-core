KV = "4.10.6"
SRCDATE = "20180412"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "40def55b6fcf589c3a121f716ac1795f"
SRC_URI[sha256sum] = "cb67e2d6c8c23691577d007df821f8b17ab9a1b7ac18308a1320b591db44b889"
