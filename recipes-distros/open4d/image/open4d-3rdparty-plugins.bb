DESCRIPTION = "3rd Party plugins for Enigma2"
MAINTAINER = "openvix team"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r4"

SRC_URI="git://github.com/oe-alliance/3rdparty-plugins.git;protocol=git"

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

DEPENDS = "enigma2 enigma2-3rdparty-plugins"

do_install() {
}

do_deploy() {
	install -d 0644 ${WORKDIR}/deploy-ipks/3rdparty
# 	install -m 0644 enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
# 	install -m 0644 enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
}

addtask deploy before do_build after do_install
