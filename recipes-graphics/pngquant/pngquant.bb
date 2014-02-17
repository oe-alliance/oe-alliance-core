DESCRIPTION = "Lossy PNG compressor"
SECTION = "libs/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=0bd68ed14aa21b08b44c4024f295a1ec"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.+git${SRCPV}"
PKGV = "2.+git${GITPKGV}"
PR = "r3"

SRC_URI = "git://github.com/pornel/pngquant.git \
           file://readonly.patch \
"

S = "${WORKDIR}/git"

inherit autotools

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${S}/pngquant ${D}/usr/bin/pngquant
}

FILES_${PN} = "/usr/bin/pngquant"

