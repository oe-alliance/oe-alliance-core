DESCRIPTION = "OpenPLi bootlogo"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "PLi team"

require conf/license/openpli-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "3.0"
PR = "r1"

S = "${WORKDIR}/"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 21 S ."

inherit update-rc.d

BOOTLOGOMVI = "${@base_contains("MACHINE_FEATURES", "hdtv", "bootlogo-hd.mvi" , "bootlogo.mvi", d)}"
# This needs a small explanation; when the machine has 'switchoff' support, it switches itself off, so we don't need switchoff.mvi...
SWITCHOFFMVI = "${@base_contains("MACHINE_FEATURES", "switchoff", "" , "switchoff.mvi", d)}"

SRC_URI = " \
	file://${BOOTLOGOMVI} \
	file://switchoff.mvi \
	file://bootlogo.jpg \
	file://bootlogo.sh \
	"

BINARY_VERSION = "1"
BINARY_VERSION_mipsel = "8"

IMAGES_VERSION = "1"

MVI = "${SWITCHOFFMVI}"
MVISYMLINKS = "bootlogo_wait backdrop"

do_install() {
        if [ -n "${BRANDINGDIR}" -a -d "${BRANDINGDIR}/bootlogo" ] ; then
                cp -p ${BRANDINGDIR}/bootlogo/* ${S}/.
        fi

	install -d ${D}/boot
	install -d ${D}/usr/share
	install -m 0755 ${S}/${BOOTLOGOMVI} ${D}/usr/share/bootlogo.mvi
	ln -sf /usr/share/bootlogo.mvi ${D}/boot/bootlogo.mvi
	for i in ${MVI}; do
		install -m 0755 ${S}/$i ${D}/usr/share/
		ln -sf /usr/share/$i ${D}/boot/$i
	done;
	for i in ${MVISYMLINKS}; do
		ln -sf /boot/bootlogo.mvi ${D}/boot/$i.mvi
		ln -sf /usr/share/bootlogo.mvi ${D}/usr/share/$i.mvi;
	done;
	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${S}/bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
}

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
