SUMMARY = "3rd Party plugins for Enigma2"
MAINTAINER = "OpenHDF Team"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

inherit gitpkgv autotools deploy

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r36"
SRC_URI="git://github.com/openhdf/3rdparty-plugins.git;protocol=git"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --with-boxtype=${MACHINEBUILD} \
"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/git"

DEPENDS = "enigma2"

THIRDPARTY_MACHINE_PLUGINS_gbquad = " \
    enigma2-plugin-extensions-multiquickbutton_*_gb_*.ipk \
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
    "
THIRDPARTY_MACHINE_PLUGINS_et6x00 = " \
    enigma2-plugin-extensions-et-*.ipk \
    enigma2-plugin-extensions-multiquickbutton_*_et_mipsel.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_et5x00 = " \
    enigma2-plugin-extensions-et-*.ipk \
    enigma2-plugin-extensions-multiquickbutton_*_et_mipsel.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_et4x00 = " \
    enigma2-plugin-extensions-et-*.ipk \
    enigma2-plugin-extensions-multiquickbutton_*_et_mipsel.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_e3hd = " \
    enigma2-plugin-extensions-hbbtv_2.13_E3HD_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_odinm7 = " \
    enigma2-plugin-extensions-hbbtv_2.12_ODIN_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_maram9 = " \
    enigma2-plugin-extensions-hbbtv_2.12_ODIN_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_starsatlx = " \
    enigma2-plugin-extensions-hbbtv_2.12_ODIN_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_classm = " \
    enigma2-plugin-extensions-hbbtv_2.12_ODIN_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_axodin = " \
    enigma2-plugin-extensions-hbbtv_2.12_ODIN_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_axase3 = " \
    enigma2-plugin-extensions-hbbtv_2.12_ODIN_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_tmtwin = " \
    enigma2-plugin-extensions-hbbtv_2.12_ODIN_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_tmsingle = " \
    enigma2-plugin-extensions-hbbtv_2.12_ODIN_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_xp100mk= " \
    enigma2-plugin-extensions-hbbtv_2.12_ODIN_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_sf8= " \
    enigma2-plugin-extensions-hbbtv_2.12_ODIN_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_xp1000= " \
    enigma2-plugin-extensions-hbbtv_2.12_ODIN_mips32el.ipk \
    "

do_install() {
}

do_deploy() {
    install -d 0755 ${DEPLOY_DIR_IPK}/3rdparty
    install -d 0755 ${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty
    install -m 0644 ${S}/*all.ipk ${DEPLOY_DIR_IPK}/3rdparty #|| true
    install -m 0644 ${S}/*mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty #|| true
    install -m 0644 ${S}/*mips32el.ipk ${DEPLOY_DIR_IPK}/3rdparty #|| true

    rm -f ${DEPLOY_DIR_IPK}/3rdparty/enigma2-plugin-extensions-et-* || true
    rm -f ${DEPLOY_DIR_IPK}/3rdparty/enigma2-plugin-extensions-hbbtv_* || true
    rm -f ${DEPLOY_DIR_IPK}/3rdparty/enigma2-plugin-extensions-multiquickbutton*.ipk || true
    rm -f ${DEPLOY_DIR_IPK}/3rdparty/enigma2-plugin-extensions-backupsuite*.ipk || true
    install -m 0644 enigma2-plugin-extensions-et-portal*.ipk ${DEPLOY_DIR_IPK}/3rdparty || true
    install -m 0644 ${S}/*${MACHINE}.ipk ${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty #|| true
    install -m 0644 ${S}/*${MACHINEBUILD}.ipk ${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty #|| true
    for i in ${THIRDPARTY_MACHINE_PLUGINS}; do
        if [ -f $i ]; then
            install -m 0644 $i ${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty || true
        fi
    done;
    for i in ${THIRDPARTY_EXTRA_PLUGINS}; do
        if [ -f $i ]; then
            install -m 0644 $i ${DEPLOY_DIR_IPK}/3rdparty || true
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
