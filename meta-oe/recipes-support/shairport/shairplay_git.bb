DESCRIPTION = "Free portable AirPlay server implementation"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7fff59c88f17faa814f26f26b06a7100"

PR = "r0"
inherit autotools pkgconfig

SRC_URI = "git://github.com/juhovh/shairplay.git"
SRCREV="64d59e3087f829006d091fa0d114efb50972a2bf"
S = "${WORKDIR}/git"


FILES_${PN} += "${libdir}/*.so"

