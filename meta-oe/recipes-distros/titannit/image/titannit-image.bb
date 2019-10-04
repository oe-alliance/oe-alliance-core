SUMMARY = "TitanNit Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "TitanNit team"

require conf/license/license-gplv2.inc

#PV = "${IMAGE_VERSION}"
#PR = "r${DATETIME}"
#PACKAGE_ARCH = "${MACHINE_ARCH}"
PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup
#    ${DISTRO}-version-info
# FIX distro-image.bb ERROR: Taskhash mismatch - part 1 add packages to build dependencies of distro-image.bb which run on end of build process
DEPENDS = " \
	${DISTRO}-base \
    "

# FIX distro-image.bb ERROR: Taskhash mismatch - part 2  make sure all do_rm_work tasks of build dependencies are finished before starting do_rootfs of distro-image.bb
do_rootfs[deptask] = "do_rm_work"

IMAGE_INSTALL = "\
	${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-extensions-enhancedmoviecenter", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "enigma2-plugin-extensions-dflash mtd-utils-jffs2", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv2", "enigma2-plugin-extensions-dbackup e2fsprogs-badblocks", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "boxmodel", "boxmodel", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "osdanimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "webkithbbtv", "enigma2-plugin-extensions-webkithbbtv", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "grautec", "enigma2-plugin-extensions-grautec", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "chromiumos", "enigma2-plugin-extensions-chromium", "", d)} \
	${DISTRO}-base \
   	titan \
   	titan-plugins \
	titan-plugin-filemanager \
	titan-plugin-imdbapi \
	titan-plugin-imdb \
	titan-plugin-mc \
	titan-plugin-networkbrowser \
	titan-plugin-readerconfig \
	titan-plugin-streaminfo \
	titan-plugin-tithek \
	titan-plugin-tmdb \
	titan-plugin-weather \
    "

IMAGE_INSTALL_append_mipsel = "\
	firmware-rtl8192cu \
	firmware-rt2870 \
	firmware-rt3070 \
	firmware-atheros-ar9271 \
	firmware-carl9170 \
	firmware-htc9271 \
	firmware-htc7010 \
	firmware-rtl8712u \
	firmware-rtl8192eu \
	kernel-module-ath9k \
	kernel-module-carl9170 \
	kernel-module-rt2800usb \
	rt3070 \
	rt8812au \
	rt8723a \
    "

IMAGE_INSTALL_append_arm = "\
	firmware-rtl8192cu \
	firmware-rt2870 \
	firmware-rt3070 \
	firmware-atheros-ar9271 \
	firmware-carl9170 \
	firmware-htc9271 \
	firmware-htc7010 \
	firmware-rtl8712u \
	firmware-rtl8192eu \
	kernel-module-ath9k \
	kernel-module-carl9170 \
	kernel-module-rt2800usb \
	rt8812au \
	rt8723a \
    "

IMAGE_INSTALL_append_sh4 = "\
	firmware-rtl8192cu \
	firmware-rt2870 \
	firmware-rt3070 \
	firmware-atheros-ar9271 \
	firmware-carl9170 \
	firmware-htc9271 \
	firmware-htc7010 \
	firmware-rtl8712u \
	firmware-rtl8192eu \
	kernel-module-rt2800usb \
	rt3070 \
	rt8812au \
	rt8723a \
    "

# Some additional comfort on the shell: Pre-install nano on boxes with 128 MB or more:
IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96", "", "nano", d)}"

# ... plus mc and helpers on 256 MB or more:
IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "mc mc-fish mc-helpers", d)}"

export IMAGE_BASENAME = "openatv-image"
# 64 or 128MB of flash: No language files, above: German and French
IMAGE_LINGUAS  = "${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "de-de fr-fr", d)}"

# Add more languages for 512 or more MB of flash:
IMAGE_LINGUAS += "${@bb.utils.contains_any("FLASHSIZE", "64 96 128 256", "", "es-es it-it nl-nl pt-pt", d)}"

IMAGE_FEATURES += "package-management"

INHIBIT_DEFAULT_DEPS = "1"

inherit image

do_package_index[nostamp] = "1"
do_package_index[depends] += "${PACKAGEINDEXDEPS}"

python do_package_index() {
    from oe.rootfs import generate_index_files
    generate_index_files(d)
}
addtask do_package_index after do_rootfs before do_image_complete

