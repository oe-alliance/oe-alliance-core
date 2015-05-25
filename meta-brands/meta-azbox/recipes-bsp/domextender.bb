NAME = "DOMExtender"
SUMMARY = "Script extended DOM for AZBoxHD"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "OpenSPA"
LICENSE = "CLOSED"

PV = "1.0"
PR = "r0"

SRC_URI = "file://S44DOMExtender.sh"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/etc/rcS.d
    install -m 0755 ${S}/S44DOMExtender.sh ${D}/etc/rcS.d
}

PACKAGE_ARCH := "${MACHINE_ARCH}"
FILES_${PN} = "/"
