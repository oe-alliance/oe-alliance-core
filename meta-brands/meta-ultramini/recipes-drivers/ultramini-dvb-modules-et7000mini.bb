KV = "4.1.21"
SRCDATE = "20160601"
GCC = "5.3.0"

SRC_URI = "http://gi-et-7000-mini.eu/${MACHINE_DRIVER}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require ultramini-dvb-modules.inc

SRC_URI[md5sum] = "dea556aab676dc159ff7d87302463a50"
SRC_URI[sha256sum] = "0c66d77ff391392a4f187d4d9f8f61d237d91dc76a6f35eeb8b1ca4fb675269f"
