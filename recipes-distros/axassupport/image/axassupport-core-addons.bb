DESCRIPTION = "core addons for Enigma2"
MAINTAINER = "AXAS Support"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r1"

SRC_URI="git://github.com/AXAS/core-addons.git;protocol=git"

EXTRA_OECONF = " \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	--with-boxtype=${MACHINE} \
"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit autotools deploy

S = "${WORKDIR}/git"

DEPENDS = "enigma2"
do_install() {
}

do_deploy() {
	install -d 0755 ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-softcams-dgcrypt-axas_1.0s_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-backupsuite-axas_1.3_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
}


addtask deploy before do_build after do_install
addtask chmod before do_build after do_package_write_ipk

do_chmod() {
	pkgdir=${DEPLOY_DIR_IPK}/3rdparty
	if [ -e $pkgdir ]; then
		chmod 0755 $pkgdir
	fi
}
