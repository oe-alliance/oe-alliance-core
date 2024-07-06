DESCIPTION = "helper tool to deliver hotplug events to e2"
MAINTAINER = "PLi team"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://bdpoll.c;startline=9;endline=20;md5=d903287e43d72c0f608fd5b718e88450"

inherit gitpkgv

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/OpenPLi/${BPN}.git;protocol=https;branch=master \
            file://fix-compile-with-gcc14.patch"

S = "${WORKDIR}/git"

inherit autotools

do_configure:prepend() {
    touch ${UNPACKDIR}/NEWS
    touch ${UNPACKDIR}/README
    touch ${UNPACKDIR}/AUTHORS
    touch ${UNPACKDIR}/ChangeLog
}

pkg_postinst:${PN} () {
    rm -f $D/autofs
    true
}
