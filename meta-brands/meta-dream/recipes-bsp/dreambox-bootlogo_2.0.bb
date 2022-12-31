SUMMARY = "Bootlogo support"
LICENSE = "CLOSED"

SRCREV = "b680994da2cc9771fb07bae0032126612ba4caf7"
SRC_URI += " file://bootlogo.scr"

inherit allarch opendreambox-github

GITHUB_PROJECT = "bootlogo"

S = "${WORKDIR}/git"

do_configure() {
}

do_install () {
    install -d ${D}${sysconfdir}/u-boot.scr.d
    install -m 0644 ${WORKDIR}/bootlogo.scr ${D}${sysconfdir}/u-boot.scr.d
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
