KV = "4.4.8"
GCC = "6.3.0"
SRCDATE = "20170424"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "d27b4e86e3a2850737f0f23535bd1847"
SRC_URI[sha256sum] = "57b8ba3deef31d50b7002d15ff5faabed218a2dffd09b8262538e7434bb27c8b"
