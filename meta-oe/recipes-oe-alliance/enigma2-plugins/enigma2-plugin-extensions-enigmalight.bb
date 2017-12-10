DESCRIPTION = "An Ambilight clone for broadcom based linux receivers."
HOMEPAGE = "https://github.com/Dima73/enigmalight"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://README;md5=93285fcad54271879db50c1fbf22d98b"

inherit gitpkgv autotools
SRCREV = "${AUTOREV}"
PV = "0.4+git${SRCPV}"
PKGV = "0.4+git${GITPKGV}"
PR = "r2"

DEPENDS = "libusb1"
RRECOMMENDS_${PN} = "python-cheetah libusb1 kernel-module-cdc-acm kernel-module-ftdi-sio kernel-module-usbserial kernel-module-ch341"

do_populate_sysroot[noexec] = "1"

do_package_qa() {
}

SRC_URI = "git://github.com/Dima73/enigmalight.git;protocol=git"

S = "${WORKDIR}/git/build"

do_configure() {
    cd ${S}
    oe_runconf
}

do_compile() {
    cd ${S}
    oe_runmake
}

do_install() {
    cd ${S}
    oe_runmake DESTDIR=${D} install
    install -d ${D}${libdir}/enigma2/python/Plugins/Extensions
    cp -R ${S}/python/plugin/EnigmaLight ${D}${libdir}/enigma2/python/Plugins/Extensions
}

FILES_${PN} += "${libdir}/enigma2/python/Plugins/Extensions/EnigmaLight/"