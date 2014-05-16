SUMMARY = "3rd Party plugins for Enigma2"
MAINTAINER = "Opendroid"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

inherit gitpkgv autotools deploy

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r1"

SRC_URI="git://github.com/opendroid-Team/3rdparty-plugins.git;protocol=git"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/git"

DEPENDS = "enigma2"


THIRDPARTY_MACHINE_PLUGINS_gbquad = " \
    enigma2-plugin-extensions-multiquickbutton_*_gb_*.ipk \
    enigma2-plugin-systemplugins-ice-network-tuner_gb*.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_gb800ue = " \
    enigma2-plugin-extensions-multiquickbutton_*_gb_*.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_gb800se = " \
    enigma2-plugin-extensions-multiquickbutton_*_gb_*.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_gb800solo = " \
    enigma2-plugin-extensions-multiquickbutton_*_gb_*.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_vusolo = " \
    enigma2-plugin-extensions-multiquickbutton_*_vu_all.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_vuduo = " \
    enigma2-plugin-extensions-multiquickbutton_*_vu_all.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_vusolo2 = " \
    enigma2-plugin-extensions-multiquickbutton_*_vu_all.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_vuduo2 = " \
    enigma2-plugin-extensions-multiquickbutton_*_vu_all.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_et9x00 = " \
    enigma2-plugin-extensions-et-*.ipk \
    enigma2-plugin-extensions-multiquickbutton_*_et_mipsel.ipk \
    enigma2-plugin-systemplugins-ice-network-tuner_et*.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_et6x00 = " \
    enigma2-plugin-extensions-et-*.ipk \
    enigma2-plugin-extensions-multiquickbutton_*_et_mipsel.ipk \
    enigma2-plugin-systemplugins-ice-network-tuner_et*.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_et5x00 = " \
    enigma2-plugin-extensions-et-*.ipk \
    enigma2-plugin-extensions-multiquickbutton_*_et_mipsel.ipk \
    enigma2-plugin-systemplugins-ice-network-tuner_et*.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_et4x00 = " \
    enigma2-plugin-extensions-et-*.ipk \
    enigma2-plugin-extensions-multiquickbutton_*_et_mipsel.ipk \
    enigma2-plugin-systemplugins-ice-network-tuner_et*.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_e3hd = " \
    enigma2-plugin-extensions-hbbtv_2.13_E3HD_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_odinm7 = " \
    enigma2-plugin-extensions-hbbtv_2.12_ODIN_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_odinm9 = " \
    enigma2-plugin-extensions-hbbtv_2.12_ODIN_mips32el.ipk \
    "
do_install() {
}

do_deploy() {
    install -d 0755 ${DEPLOY_DIR_IPK}/3rdparty
    install -d 0755 ${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty
    install -m 0644 *.ipk ${DEPLOY_DIR_IPK}/3rdparty
    rm ${DEPLOY_DIR_IPK}/3rdparty/enigma2-plugin-extensions-et-*
    rm ${DEPLOY_DIR_IPK}/3rdparty/enigma2-plugin-extensions-hbbtv_*
    rm ${DEPLOY_DIR_IPK}/3rdparty/enigma2-plugin-extensions-multiquickbutton*.ipk
    rm ${DEPLOY_DIR_IPK}/3rdparty/enigma2-plugin-extensions-backupsuite*.ipk
    install -m 0644 enigma2-plugin-extensions-et-portal_inofficial-3.3.1_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
    for i in ${THIRDPARTY_MACHINE_PLUGINS}; do
        if [ -f $i ]; then
            install -m 0644 $i ${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty;
        fi
    done;
    for i in ${THIRDPARTY_EXTRA_PLUGINS}; do
        if [ -f $i ]; then
            install -m 0644 $i ${DEPLOY_DIR_IPK}/3rdparty;
        fi
    done;
    pkgdir=${DEPLOY_DIR_IPK}/3rdparty
    if [ -e $pkgdir ]; then
        chmod 0755 $pkgdir
    fi
    pkgdir=${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty
    if [ -e $pkgdir ]; then
        chmod 0755 $pkgdir
    fi
}

addtask do_deploy before do_package_write after do_package_write_ipk
