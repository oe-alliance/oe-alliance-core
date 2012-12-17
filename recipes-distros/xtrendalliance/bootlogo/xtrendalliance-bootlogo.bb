DESCRIPTION = "Xtrend-Alliance bootlogo"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "Xtrend-Alliance"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "1.0"
PR = "r4"

S = "${WORKDIR}/"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 05 S ."

inherit update-rc.d

SRC_URI = " \
			file://bootlogo.mvi \
			file://splash.bin \
			file://bootlogo.sh \
"

BINARY_VERSION = "1"
BINARY_VERSION_mipsel = "9"

IMAGES_VERSION = "1"

MVISYMLINKS = "bootlogo_wait backdrop switchoff"

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

PACKAGE_ARCH := "${MACHINE_ARCH}"
FILES_${PN} = "/boot /usr/share /etc/init.d"

inherit deploy
do_deploy() {
	if [ -e splash.bin ]; then
		install -m 0644 splash.bin ${DEPLOYDIR}/splash.bin
	fi
}

addtask deploy before do_build after do_install
