DESCRIPTION = "dependency for kodi"
SECTION = "console/utils"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://README.md;md5=c08a00775d3b1a37e4e61b814592f1e8"

PV = "0.2.0"
PR = "r0"

SRC_URI = "http://ftp.halifax.rwth-aachen.de/xbmc/build-deps/sources/libdcadec-git-2a9186e3.tar.gz \
           file://dcadec.pc"

SRC_URI[md5sum] = "32cd29c9e8f2f7e15de072903debd942"
SRC_URI[sha256sum] = "b0833d710d99cb441194343224331c91a161fb774888aca7168d5c2d18876561"

FILES_${PN} = "/"

S = "${WORKDIR}/dcadec-master"

do_compile_prepend() {
    cp ${WORKDIR}/dcadec.pc ${S}/dcadec.pc
}

do_compile() {
    export CONFIG_SHARED="TRUE"
    make
}

do_install() {
    install -d ${D}/${libdir}
    install -d ${D}/${libdir}/pkgconfig
    install -d ${D}/${includedir}/libdcadec
    install -d ${D}/${bindir}
    install -m 0644 ${S}/libdcadec/dca_context.h ${D}/${includedir}/libdcadec/
    install -m 0644 ${S}/libdcadec/dca_frame.h ${D}/${includedir}/libdcadec/
    install -m 0644 ${S}/libdcadec/dca_stream.h ${D}/${includedir}/libdcadec/
    install -m 0644 ${S}/libdcadec/dca_waveout.h ${D}/${includedir}/libdcadec/
    install -m 0644 ${S}/dcadec.pc ${D}/${libdir}/pkgconfig/dcadec.pc
    install -m 0755 ${S}/libdcadec/libdcadec.so* ${D}/${libdir}/
    install -m 0755 ${S}/dcadec ${D}/${bindir}/
}

do_package_qa() {
}
