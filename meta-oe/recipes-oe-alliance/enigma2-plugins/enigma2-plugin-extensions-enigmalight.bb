DESCRIPTION = "An Ambilight clone for broadcom based linux receivers."
HOMEPAGE = "https://github.com/Dima73/enigmalight"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://README;md5=93285fcad54271879db50c1fbf22d98b"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS = "libusb1"
RRECOMMENDS_${PN} = "${PYTHON_PN}-cheetah libusb1 kernel-module-cdc-acm kernel-module-ftdi-sio kernel-module-usbserial kernel-module-ch341"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"

SRC_URI = "git://github.com/Dima73/enigmalight.git;protocol=git \
        file://import-version-from-twisted.patch"

S = "${WORKDIR}/git/build"

inherit autotools

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

pkg_postrm_${PN}() {
#!/bin/sh
rm -rf ${libdir}/enigma2/python/Plugins/Extensions/EnigmaLight/
echo "Plugin removed! You should restart enigma2 now!"
exit 0
}

do_populate_sysroot[noexec] = "1"

do_package_qa() {
}
