MAINTAINER = "Narcis Ilisei"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=3c34afdc3adf82d2448f12715a255122"

PV = "v.02.24.38"
PR = "r4"

SRC_URI = "${SOURCEFORGE_MIRROR}/${PN}/${PN}/${PN}.${PV}/${PN}.${PV}.tar.gz \
    file://inadyn-mt.sh \
    file://inadyn.conf \
    file://remove_host_include_paths.patch \
    "

SRC_URI[md5sum] = "e868ab86df2eb20a1d98c11e8564e52c"
SRC_URI[sha256sum] = "3a1028218e395cfda981fd912d2c097d3433037e62ca0594ea0f3bdf2b7bf0a4"

S = "${WORKDIR}/${PN}.${PV}"

inherit autotools-brokensep update-rc.d

INITSCRIPT_NAME = "inadyn-mt"
CONFFILES_${PN} = "/etc/inadyn.conf"

do_compile() {
    make -f makefile-deprecated
}

do_install() {
    install -d ${D}/usr/bin
    install -m 755 ${S}/bin/linux/inadyn-mt ${D}/usr/bin
    install -d ${D}/etc
    install -m 644 ${WORKDIR}/inadyn.conf ${D}/etc/
    install -d ${D}/etc/init.d
    install -m 755 ${WORKDIR}/inadyn-mt.sh ${D}/etc/init.d/inadyn-mt
}
