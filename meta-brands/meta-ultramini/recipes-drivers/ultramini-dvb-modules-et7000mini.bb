KV = "4.1.21"
SRCDATE = "20160613"
GCC = "5.3.0"

SRC_URI = "http://gi-et-7000-mini.eu/${MACHINE_DRIVER}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require ultramini-dvb-modules.inc

SRC_URI[md5sum] = "9f3deb442747a0267f063585fefe4b41"
SRC_URI[sha256sum] = "e6b582551075412a4873e49e5c837d64289c0f7b96b7f0a108612850f1b7ceef"
