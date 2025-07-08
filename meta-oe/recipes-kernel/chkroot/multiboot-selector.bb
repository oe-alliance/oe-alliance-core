SUMMARY = "multiboot-selector for dreamos"
MAINTAINER = "oe-a"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r1"

SRC_URI = "file://multiboot-selector.sh"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_compile() {
}

do_install() {
    install -d ${D}/usr/bin/
    install -m 0755 ${S}/multiboot-selector.sh ${D}/usr/bin/multiboot-selector.sh
}

INSANE_SKIP = "file-rdeps"
