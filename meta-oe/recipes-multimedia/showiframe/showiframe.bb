SUMMARY = "utility to show an mpeg2/4 iframe on a linuxtv video device"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://showiframe.c;firstline=1;endline=1;md5=d67f9281bc4bfeee90913721aa741a8b"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.4"

SRC_URI = "file://showiframe.c"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_compile() {
    ${CC} -o showiframe showiframe.c
}

do_install() {
    install -d ${D}/${bindir}/
    install -m 0755 ${UNPACKDIR}/showiframe ${D}/${bindir}/
}

INSANE_SKIP:${PN} += "ldflags"