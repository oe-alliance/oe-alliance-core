DESCRIPTION = "dependency for kodi"
SECTION = "console/utils"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://README;md5=2a51a796ca47e91336a4d198147ba58f"

PV = "1.0"
PR = "r0"

SRCREV = "1657e4659de5143da68e0f39b265f7eeb3c66c11"
SRC_URI = "git://github.com/mx3L/libsquish;protocol=git;branch=master"
FILES_${PN} = "${includedir}/squish.h ${libdir}/pkgconfig/squish.pc ${libdir}/libsquish.a"

S = "${WORKDIR}/git"

do_compile_prepend() {
    cp ${S}/config.in ${S}/config
}

do_install() {
    cd ${S}
    install -d ${D}/${libdir}
    install -d ${D}/${libdir}/pkgconfig
    install -d ${D}/${includedir}
    install -m 0644 squish.h ${D}/${includedir}/
    install -m 0644 squish.pc ${D}/${libdir}/pkgconfig/
    install -m 0755 libsquish.a ${D}/${libdir}/
}
