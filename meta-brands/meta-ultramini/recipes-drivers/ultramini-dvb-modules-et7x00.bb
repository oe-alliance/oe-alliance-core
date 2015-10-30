KV = "3.18.18"
SRCDATE = "20151028"

SRC_URI = "http://source.mynonpublic.com/ultramini/${MACHINE_DRIVER}-drivers-${KV}-${SRCDATE}.zip"
require ultramini-dvb-modules.inc

SRC_URI[md5sum] = "f4414bff566bfda2f9e5dfd55b5e153a"
SRC_URI[sha256sum] = "866e85e53b95733a17e10c00c4f6054cb66bd832abe3fa7b8e0bc84ffa8fb54b"
