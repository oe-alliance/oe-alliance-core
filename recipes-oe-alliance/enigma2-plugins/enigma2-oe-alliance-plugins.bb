DESCRIPTION = "Additional plugins for Enigma2"
MAINTAINER = "oe-alliance team"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

PROVIDES = "${PN} \
	enigma2-plugin-systemplugins-blindscan \
	enigma2-plugin-extensions-dlnabrowser \
	enigma2-plugin-extensions-dlnaserver  \
	enigma2-plugin-systemplugins-firmwareupgrade \
	enigma2-plugin-systemplugins-fpgaupgrade \
	enigma2-plugin-systemplugins-vfdcontrol \
	enigma2-plugin-extensions-streamtv \
	enigma2-plugin-systemplugins-fancontrol \
	enigma2-plugin-extensions-vuplusevent \
	enigma2-plugin-systemplugins-remotecontrolcode \
	"

DESCRIPTION_enigma2-plugin-systemplugins-blindscan = "blindscan..."
RDEPENDS_enigma2-plugin-systemplugins-blindscan = "virtual/blindscan-dvbs"
DESCRIPTION_enigma2-plugin-extensions-dlnabrowser = "this is dlna/upnp browser using djmount"
RDEPENDS_enigma2-plugin-extensions-dlnabrowser = "djmount fuse-utils libfuse2 libupnp3 gst-plugins-bad-neonhttpsrc"
DESCRIPTION_enigma2-plugin-extensions-dlnaserver = "this is dlna server using minidlna"
RDEPENDS_enigma2-plugin-extensions-dlnaserver = "minidlna"
DESCRIPTION_enigma2-plugin-systemplugins-firmwareupgrade = "Upgrade your system Firmware"
DESCRIPTION_enigma2-plugin-systemplugins-fpgaupgrade = "Upgrade your system FPGA"
DESCRIPTION_enigma2-plugin-systemplugins-vfdcontrol = "vfd controller"
RDEPENDS_enigma2-plugin-systemplugins-vfdcontrol = "gigablue-vfdctl"
DESCRIPTION_enigma2-plugin-extensions-streamtv = "iptv player"
RDEPENDS_enigma2-plugin-extensions-streamtv = "librtmp"
DESCRIPTION_enigma2-plugin-systemplugins-fancontrol = "Control your internal system fan."
RDEPENDS_enigma2-plugin-systemplugins-fancontrol_et9x00 = "hddtemp"
DESCRIPTION_enigma2-plugin-extensions-vuplusevent = "Return the Love Event (only for genuine box)"
DESCRIPTION_enigma2-plugin-systemplugins-remotecontrolcode = "Change Remote Control Code"

DEPENDS = "enigma2 \
	${@base_contains("MACHINE_FEATURES", "blindscan-dvbc", "virtual/blindscan-dvbc" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "virtual/blindscan-dvbs" , "", d)} \
	djmount \
	librtmp \
	minidlna \
	hddtemp \
	"

inherit gitpkgv autotools

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r11"

SRC_URI="git://github.com/oe-alliance/oe-alliance-plugins.git;protocol=git"

EXTRA_OECONF = " \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	--with-po \
	--with-boxtype=${MACHINE}"

ALLOW_EMPTY_${PN} = "1"
PACKAGES += "${PN}-meta"
FILES_${PN}-meta = "${datadir}/meta"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/git"

python populate_packages_prepend() {
	enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}
