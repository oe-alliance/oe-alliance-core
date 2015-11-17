KV = "3.18.24"
SRCDATE = "20151109"

SRC_URI = "http://source.mynonpublic.com/ultramini/${MACHINE_DRIVER}-drivers-${KV}-${SRCDATE}.zip"
require ultramini-dvb-modules.inc

SRC_URI[md5sum] = "22a10f5310649afdb72dbd2f49c897a5"
SRC_URI[sha256sum] = "4a433619a9a7efc16c7eeca4ca0505009a23cfda7e1edf4e18af4a03fb3380e8"
