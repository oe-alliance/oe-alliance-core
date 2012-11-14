DESCRIPTION = "3rd Party plugins for Enigma2"
MAINTAINER = "opensif team"

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

inherit autotools

S = "${WORKDIR}/git"

DEPENDS = "enigma2 enigma2-3rdparty-plugins"

do_install_vuuno() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
do_install_vuultimo() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
do_install_vusolo() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
do_install_vuduo() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_et4x00() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
do_install_et5x00() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
do_install_et6x00() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
do_install_et9x00() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_tmtwin() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
do_install_tm2t() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
do_install_tmsingle() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_gb800se() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
do_install_gb800ue() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
do_install_gb800solo() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
do_install_gbquad() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_maram9() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_ventonhdx() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_dm8000() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
do_install_dm7020hd() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
do_install_dm500hd() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
do_install_dm800se() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
do_install_dm800() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-suomipoeka_1.0.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-enhancedpowersave_2.2.1-20120715_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
}
