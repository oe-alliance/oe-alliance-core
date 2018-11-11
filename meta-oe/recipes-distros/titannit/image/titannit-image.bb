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
    titan-bin \
    "

# FIX distro-image.bb ERROR: Taskhash mismatch - part 2  make sure all do_rm_work tasks of build dependencies are finished before starting do_rootfs of distro-image.bb
do_rootfs[deptask] = "do_rm_work"

IMAGE_INSTALL = "\
	${DISTRO}-base \
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
#	titan-gmediarender
#	${@bb.utils.contains('MACHINE', 'disabled-ipv6', '', 'kernel-module-ipv6', d)}
#	${@bb.utils.contains('MACHINE', 'disabled-ath9k', '', 'kernel-module-ath9k-htc', d)}
#	${@bb.utils.contains('MACHINE', 'disabled-wlanbuild', '', 'kernel-module-rtl8192cu', d)}
#	${@bb.utils.contains("MACHINE_FEATURES", "linuxwifi", "kernel-module-rtl8xxxu", "rtl8192eu", d)}
#	${@bb.utils.contains("MACHINE_FEATURES", "wifiusblegacy", "rtl871x", "kernel-module-r8712u", d)}
#	${@bb.utils.contains('MACHINE', 'disabled-build', '', 'packagegroup-base', d)}

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
#	titan-gmediarender
#	${@bb.utils.contains('MACHINE', 'disabled-ipv6', '', 'kernel-module-ipv6', d)}
#	${@bb.utils.contains('MACHINE', 'disabled-ath9k', '', 'kernel-module-ath9k-htc', d)}
#	${@bb.utils.contains('MACHINE', 'disabled-wlanbuild', '', 'kernel-module-rtl8192cu', d)}
#	${@bb.utils.contains("MACHINE_FEATURES", "linuxwifi", "kernel-module-rtl8xxxu", "rtl8192eu", d)}
#	${@bb.utils.contains('MACHINE', 'disabled-rt3070', '', 'rt3070', d)}
#	${@bb.utils.contains("MACHINE_FEATURES", "wifiusblegacy", "rtl871x", "kernel-module-r8712u", d)}
#	${@bb.utils.contains('MACHINE', 'disabled-build', '', 'packagegroup-base', d)}

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
#	titannit-version-info
#	${@bb.utils.contains("MACHINE_FEATURES", "linuxwifi", "kernel-module-rtl8xxxu", "rtl8192eu", d)}

#
#    python
#    python-codecs
#    python-compression
#    python-core
#    python-crypt
#    python-fcntl
#    python-lang
#    python-netclient
#    python-netserver
#    python-pickle
#    python-re
#    python-shell
#    python-threading
#    python-twisted-core
#    python-twisted-web
#    python-utf8-hack
#    python-xml
#    python-zlib
#    python-zopeinterface
#    python-email
#    python-mime
#    python-pyusb
#    python-subprocess
#    python-process
#    python-imaging
#	${@bb.utils.contains("MACHINE_FEATURES", "dreambox", "", "ofgwrite", d)} \
# disable for packagegroup-base
#	libcrypto-compat-0.9.7
#	libcrypto-compat-0.9.8
# disable for dm7200hd
#	kernel-module-rtl8192cu
# disabled building on svn
#	${@bb.utils.contains('MACHINE', 'vusolo2', 'titan-xbmc-helix', '', d)} \
#	${@bb.utils.contains('MACHINE', 'inihdp', 'titan-xbmc-nightly', '', d)} \
#    snes9x-sdl
#    libavahi-client
#    minidlna
#    ${@bb.utils.contains('MACHINEBUILD', 'atemionemesis', '', 'titan-xbmc', d)} \
#    ${@base_conditional('MACHINE', 'inihdp', '', 'titan-xbmc', d)} \
#    ${@bb.utils.contains('MACHINEBUILD', 'atemionemesis', '', 'titan-xbmc', d)} \
#    titan-bin
#    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "gst-plugin-libxt" , "", d)} \
#    titan-plugins
#    enigma2-locale-meta test fpr glibc only

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

