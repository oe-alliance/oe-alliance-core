SUMMARY = "Bootlogo support"
LICENSE = "CLOSED"

SRCREV = "a0bf37c5b20c1db4dc0008882cc5494b475be0c1"
SRC_URI += " file://bootlogo.scr"

inherit allarch opendreambox-git

OPENDREAMBOX_PROJECT = "bootlogo"

S = "${WORKDIR}/git"

do_configure() {
}

do_install () {
    install -d ${D}${sysconfdir}/u-boot.scr.d
    install -m 0644 ${UNPACKDIR}/bootlogo.scr ${D}${sysconfdir}/u-boot.scr.d
    install -d ${D}/boot
    install -m 0644 ${S}/bootlogo.bmp ${D}/boot/bootlogo.bmp
}

PACKAGES =+ "${PN}-u-boot"

FILES:${PN}-u-boot = "${sysconfdir}/u-boot.scr.d"
FILES:${PN} = "/boot/bootlogo.bmp"

RDEPENDS:${PN}-u-boot = "flash-scripts"

RRECOMMENDS:${PN} = " ${PN}-u-boot"

pkg_postinst:${PN}-u-boot() {
[ -n "$D" ] || update-autoexec
}
