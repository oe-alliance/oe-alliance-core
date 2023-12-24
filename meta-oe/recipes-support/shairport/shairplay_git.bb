DESCRIPTION = "Free portable AirPlay server implementation"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7fff59c88f17faa814f26f26b06a7100"

DEPENDS += "avahi libao"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.9.0+git"
PKGV = "0.9.0+git${GITPKGV}"

SRC_URI = "git://github.com/juhovh/shairplay.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

# needed for configure to find dns_sd header in nonstandard includes path
CFLAGS += "-I${STAGING_INCDIR}/avahi-compat-libdns_sd"

do_install:append(){
    install -d ${D}/${includedir}/shairplay
    install -m 0644 ${S}/include/shairplay/*.h ${D}/${includedir}/shairplay/
}

FILES:${PN} += "${libdir}/*.so ${includedir}/shairplay/*.h"
