KV = "4.1.21"
SRCDATE = "20170318"
GCC = "6.3.0"

SRC_URI = "http://gi-et.info/et7000mini/${MACHINE_DRIVER}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require ultramini-dvb-modules.inc

SRC_URI[md5sum] = "4f4a6d01a973e7527e3782b2ffbaae64"
SRC_URI[sha256sum] = "38d4f13c24af6d72a6d80fa0a3eedee4d2d64bc93f9dd1b3c9ba190b0d18e89f"
