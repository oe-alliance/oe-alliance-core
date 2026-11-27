SUMMARY = "Configure the GTX HDMI framebuffer for Enigma2 and recovery"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
COMPATIBLE_MACHINE = "^gtxcombo$"
PACKAGE_ARCH = "${MACHINE_ARCH}"
SRC_URI = "file://gtx-hdmimode.c"
S = "${UNPACKDIR}"
DEPENDS = "libdrm"
inherit pkgconfig

RREPLACES:${PN} = "gtx-fbconsole"
RCONFLICTS:${PN} = "gtx-fbconsole"
RPROVIDES:${PN} = "gtx-fbconsole"

do_compile() {
    ${CC} ${CPPFLAGS} ${CFLAGS} gtx-hdmimode.c -o gtx-hdmimode ${LDFLAGS} $(pkg-config --cflags --libs libdrm)
}

do_install() {
    install -Dm0755 gtx-hdmimode ${D}${sbindir}/gtx-hdmimode
}
