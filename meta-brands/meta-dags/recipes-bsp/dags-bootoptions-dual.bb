SUMMARY = "Multiboot for Hisilicon ${MACHINEBUILD}"
LICENSE = "CLOSED"
PRIORITY = "required"
SECTION = "base"
PACKAGE_ARCH = "${MACHINEBUILD}"
require conf/license/license-close.inc

PR = "20220402"

SRC_URI = "https://source.mynonpublic.com/dags/dags-bootoptions-${PR}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_compile() {
}

do_install() {
	install -d ${D}/boot/
	install -m 0755 ${S}/STARTUP* ${D}/boot/
}

do_package_qa() {
}

INSANE_SKIP:${PN} += "already-stripped"
INHIBIT_PACKAGE_STRIP = "1"

SRC_URI[md5sum] = "79129766eda48ab19a3e36d88564afb1"
SRC_URI[sha256sum] = "0dcae13526db0981a0900ce7102f2eb11330e06bf42e687396f1046a25491293"

FILES:${PN} += "/boot/"
