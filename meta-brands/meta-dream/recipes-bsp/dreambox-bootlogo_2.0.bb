SUMMARY = "Bootlogo support"
require conf/license/license-close.inc

SRCREV = "a0bf37c5b20c1db4dc0008882cc5494b475be0c1"
SRC_URI += " file://bootlogo.scr"

inherit allarch opendreambox-git

DEPENDS += "u-boot-tools-native"

OPENDREAMBOX_PROJECT = "bootlogo"

do_configure() {
}

do_install () {
    install -d ${D}${sysconfdir}/u-boot.scr.d
    install -m 0644 ${UNPACKDIR}/bootlogo.scr ${D}${sysconfdir}/u-boot.scr.d
    install -d ${D}/boot
    install -m 0644 ${S}/bootlogo.bmp ${D}/boot/bootlogo.bmp
}

PACKAGES =+ "${PN}-u-boot"

FILES:${PN}-u-boot = "${sysconfdir}/u-boot.scr.d /boot/autoexec.img"
FILES:${PN} = "/boot/bootlogo.bmp"

RDEPENDS:${PN}-u-boot = "flash-scripts"

RRECOMMENDS:${PN} = " ${PN}-u-boot"

do_install:append() {
    LC_ALL=C grep -h "^[a-zA-Z0-9]" ${D}${sysconfdir}/u-boot.scr.d/bootlogo.scr > ${B}/autoexec.in
    if [ -s ${B}/autoexec.in ]; then
        install -d ${D}/boot
        uboot-mkimage -A arm64 -O linux -T script -C none -n autoexec -d ${B}/autoexec.in ${D}/boot/autoexec.img
    fi
}

pkg_postinst:${PN}-u-boot() {
[ -n "$D" ] || update-autoexec
}
