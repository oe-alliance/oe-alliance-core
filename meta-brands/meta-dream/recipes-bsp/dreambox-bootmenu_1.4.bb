SUMMARY = "Boot menu for the Dreambox One and Two"
DESCRIPTION = "Replaces the boot menu built into u-boot on boxes with the GPT multiboot layout"
LICENSE = "GPL-3.0-only AND Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c79ff39f19dfec6d293b95dea7b07891 \
    file://LICENSE.fonts;md5=89aea4e17d99a7cacdbeed46a0096b10 \
"

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit github-releases

GITHUB_BASE_URI = "https://github.com/oe-alliance/dreambox-bootmenu/releases/"
SRC_URI = "${GITHUB_BASE_URI}download/v${PV}/${BPN}.tar.gz;downloadfilename=${BP}.tar.gz"
SRC_URI[sha256sum] = "b754286e3a4b8a0cd2e2554364003801a55d6fee88bf9910392a3c1a03c60cb0"
S = "${UNPACKDIR}/${BPN}"

RDEPENDS:${PN} = "u-boot-tools-mkimage"

# bootmenu.bin is a raw u-boot standalone program, not an ELF file.
INSANE_SKIP:${PN} = "arch"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}/data/bootmenu
    install -m 0644 ${S}/bootmenu.bin ${S}/bootmenu.img ${S}/bootlogo.bmp ${S}/bootmenu.scr ${D}/data/bootmenu/
    install -m 0755 ${S}/install.sh ${D}/data/bootmenu/
    install -m 0644 ${S}/NOTICE ${S}/LICENSE ${S}/LICENSE.fonts ${D}/data/bootmenu/
}

FILES:${PN} = "/data/bootmenu"

# The files belong on the data partition shared by all slots, never into the
# root file system of the running image.
pkg_preinst:${PN}() {
#!/bin/sh
[ -n "$D" ] && exit 0
if ! grep -q " /data " /proc/mounts; then
    echo "dreambox-bootmenu: /data is not mounted, not installing."
    exit 1
fi
if [ "$(dd if=/dev/mmcblk0 bs=1 skip=512 count=8 2>/dev/null)" != "EFI PART" ] ||
   ! grep -q "^PARTNAME=dreambox-rootfs1$" /sys/class/block/mmcblk0p6/uevent 2>/dev/null; then
    echo "dreambox-bootmenu: no GPT multiboot layout, not installing."
    exit 1
fi
}

# Puts the stub into the image in the second slot, if it has none yet, and
# rebuilds its autoexec.img; install.sh checks the layout and the slot first
# and keeps a copy of bootconfig.txt for the menu to fall back on.
pkg_postinst:${PN}() {
#!/bin/sh
[ -n "$D" ] && exit 0
sh /data/bootmenu/install.sh --no-download || true
}

# Takes the stub out of the second slot again, unless an image package brings
# it; an upgrade keeps everything.
pkg_prerm:${PN}() {
#!/bin/sh
[ -n "$D" ] && exit 0
[ "$1" = "remove" ] || exit 0
sh /data/bootmenu/install.sh --remove || true
}
