SUMMARY = "utility to show an mpeg2/4 iframe on a linuxtv video device"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://showiframe.c;firstline=1;endline=1;md5=d67f9281bc4bfeee90913721aa741a8b"
PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS_wetekplay = "libamcodec"

PV = "1.3"
PR = "r2"

SRC_URI = "file://showiframe.c"

SRC_URI_append_wetekplay = " file://showiframe.patch"

S = "${WORKDIR}"

do_compile() {
    ${CC} -o showiframe showiframe.c
}

do_compile_wetekplay() {
    ${CC} -I${STAGING_INCDIR}/amlogic/amcodec -lamcodec -o showiframe showiframe.c
}


do_install() {
    install -d ${D}/${bindir}/
    install -m 0755 ${S}/showiframe ${D}/${bindir}/
}
