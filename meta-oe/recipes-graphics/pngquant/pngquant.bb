SUMMARY = "Lossy PNG compressor"
SECTION = "libs/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=ac8a52bd839aac2f67712c967b3ff86a"
DEPENDS = "libpng" 
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit gitpkgv
SRCREV = "ee7bef8644ad1bac3fce2c04afef13f1ec19166b"
PV = "2.+git${SRCPV}"
PKGV = "2.+git${GITPKGV}"

SRC_URI = "git://github.com/kornelski/pngquant.git;protocol=https;branch=main \
           file://readonly.patch \
"

S = "${WORKDIR}/git"

inherit autotools-brokensep

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${S}/pngquant ${D}/usr/bin/pngquant
}

FILES_${PN} = "/usr/bin/pngquant"

