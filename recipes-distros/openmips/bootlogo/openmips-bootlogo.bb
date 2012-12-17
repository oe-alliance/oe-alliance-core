DESCRIPTION = "openMips bootlogo"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openMips"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "1.0"
PR = "r4"

S = "${WORKDIR}/"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 05 S ."

inherit update-rc.d

SRC_URI = " \
		file://bootlogo.sh \
		 "

SRC_URI_append_et9x00 = " \
					file://bootlogo.mvi \
					file://splash.bin \
					"		 

SRC_URI_append_gb800se = " \
					file://bootlogo.mvi \
					file://splash.bin \
					"
SRC_URI_append_gb800solo = " \
					file://bootlogo.mvi \
					file://splash.bin \
					"
SRC_URI_append_gb800ue = " \
					file://bootlogo.mvi \
					file://splash.bin \
					file://lcdsplash.bin \
					"
SRC_URI_append_gbquad = " \
					file://bootlogo.mvi \
					file://splash.bin \
					file://lcdsplash.bin \
					"

BINARY_VERSION = "1"
BINARY_VERSION_mipsel = "8"

IMAGES_VERSION = "1"

MVISYMLINKS = "bootlogo_wait backdrop switchoff.mvi"

do_install() {
	install -d ${D}/boot
	install -d ${D}/usr/share
	install -m 0755 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
	ln -sf /usr/share/bootlogo.mvi ${D}/boot/bootlogo.mvi
	for i in ${MVISYMLINKS}; do
		ln -sf /boot/bootlogo.mvi ${D}/boot/$i.mvi
		ln -sf /usr/share/bootlogo.mvi ${D}/usr/share/$i.mvi;
	done;
	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${S}/bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
}

inherit deploy
do_deploy() {
	if [ -e splash.bin ]; then
		install -m 0644 splash.bin ${DEPLOYDIR}/splash.bin
	fi
	if [ -e lcdsplash.bin ]; then
		install -m 0644 lcdsplash.bin ${DEPLOYDIR}/lcdsplash.bin
	fi
}

addtask deploy before do_build after do_install

pkg_preinst() {
	[ -d /proc/stb ] && mount -t jffs2 mtd:'boot partition' /boot
	true
}

pkg_postinst() {
	[ -d /proc/stb ] && umount /boot
	true
}

pkg_prerm() {
	[ -d /proc/stb ] && mount -t jffs2 mtd:'boot partition' /boot
	true
}

pkg_postrm() {
	[ -d /proc/stb ] && umount /boot
	true
}

PACKAGE_ARCH := "${MACHINE_ARCH}"
FILES_${PN} = "/boot /usr/share /etc/init.d"
