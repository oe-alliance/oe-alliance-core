SUMMARY = "Amlogic SM1 kernel for the GTMedia GTX Combo"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit gitpkgv kernel kernel-fixups

COMPATIBLE_MACHINE = "^gtxcombo$"

SRC_URI = "git://github.com/oe-alliance-kernels/linux-amlogic.git;protocol=https;branch=${PV}"
SRCREV = "${AUTOREV}"
PKGV = "${PV}+git${GITPKGV}"
PR = "r1"
S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}"

DEPENDS += "flex-native openssl-native"

# OE installs DTBs from arch/arm64/boot/dts. Keep the vendor header search path.
EXTRA_OEMAKE += "dtstree=arch/arm64/boot/dts"

do_configure:prepend() {
    install -m 0644 ${S}/arch/arm64/configs/gtxcombo_defconfig ${B}/.config
    ${S}/scripts/config --file ${B}/.config --set-str MODPROBE_PATH "${base_sbindir}/modprobe"
    ${S}/scripts/config --file ${B}/.config --keep-case --module MT76x0U
}
