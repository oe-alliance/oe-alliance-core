DESCRIPTION = "Free portable AirPlay server implementation"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7fff59c88f17faa814f26f26b06a7100"

inherit autotools pkgconfig

SRC_URI = "git://github.com/juhovh/shairplay.git"

SRCREV="498bc5bcdd305e04721f94a04b9f26a7da72673f"

S = "${WORKDIR}/git"

do_install_append(){
    install -d ${D}/${includedir}/shairplay
    install -m 0644 ${S}/include/shairplay/*.h ${D}/${includedir}/shairplay/
}

FILES_${PN} += "${libdir}/*.so ${includedir}/shairplay/*.h"
