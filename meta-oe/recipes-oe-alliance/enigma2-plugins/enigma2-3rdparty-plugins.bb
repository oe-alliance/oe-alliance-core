SUMMARY = "3rd Party plugins for Enigma2"
MAINTAINER = "oe-alliance team"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

inherit gitpkgv deploy

DEPENDS = "tslib mpfr gmp"

SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+gitr${SRCPV}"
PKGV = "${IMAGE_VERSION}+gitr${GITPKGV}"
PR = "r212"

SRC_URI="git://github.com/oe-alliance/3rdparty-plugins.git;protocol=git"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/git"


# Info: enigma2-plugin-extensions-transmission_2.92-r2_all.ipk  have update depends dont merge to old oe-a versions

THIRDPARTY_PLUGINS = " \
    enigma2-plugin-extensions-atmolightd_0.7-pre22_all.ipk \
    enigma2-plugin-extensions-blackout-blind_1.3_all.ipk \
    enigma2-plugin-extensions-bluray_2.0rc1_all.ipk \
    enigma2-plugin-extensions-boblight-enigma2_0.8r8_mips32el.ipk \
    enigma2-plugin-extensions-camofs_9.19_all.ipk \
    enigma2-plugin-extensions-clevertanken_0.8rc2_all.ipk \
    enigma2-plugin-extensions-csfd_11-55-20160929_all.ipk \
    enigma2-plugin-extensions-customsubservices_0.1.3_all.ipk \
    enigma2-plugin-extensions-cyrussettings_1.0.0_all.ipk \
    enigma2-plugin-extensions-digitalfernsehen_1.2rc1_all.ipk \
    enigma2-plugin-extensions-dvrproviderplayer_2.06_all.ipk \
    enigma2-plugin-extensions-ehue_0.2-r0_all.ipk \
    enigma2-plugin-extensions-filesearchfs_3.0_all.ipk \
    enigma2-plugin-extensions-focusonline_1.1rc4_all.ipk \
    enigma2-plugin-extensions-fragmutti_1.0rc2_all.ipk \
    enigma2-plugin-extensions-gmailreader-oe2.0_1.3_all.ipk \
    enigma2-plugin-extensions-googlenewsreader_1.0_mips32el.ipk \
    enigma2-plugin-extensions-hdmitest_0.4_mipsel.ipk \
    enigma2-plugin-extensions-hetweer_4.7r01_all.ipk \
    enigma2-plugin-extensions-ipchecker_003_all.ipk \
    ${@bb.utils.contains("TARGET_ARCH", "sh4", "enigma2-plugin-extensions-isettinge2-3.4.4-oe2.0-all-sh4.ipk" , "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "enigma2-plugin-extensions-isettinge2-3.4.4-oe2.0-all-mips.ipk" , "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "arm", "enigma2-plugin-extensions-isettinge2-3.4.4-oe2.0-all-arm.ipk" , "", d)} \
    enigma2-plugin-extensions-kicker_3.6rc5_all.ipk \
    enigma2-plugin-extensions-livefootball_7.1_all.ipk \
    enigma2-plugin-extensions-mediainfo_3.0.7_all.ipk \
    enigma2-plugin-extensions-mediaportal_all.ipk \
    enigma2-plugin-extensions-moviebrowser_3.7rc3_all.ipk \
    enigma2-plugin-extensions-mp3browser_2.0rc1_all.ipk \
    enigma2-plugin-extensions-mspfs_1.06_all.ipk \
    enigma2-plugin-extensions-netspeedtest_1.0rc1_all.ipk \
    ${@bb.utils.contains('MACHINE_BRAND', 'Vu+', '' , 'enigma2-plugin-extensions-openopera_1.0-r0_mips32el.ipk', d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "enigma2-plugin-extensions-opkg-tools_1.4_mips32el.ipk" , "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "arm", "enigma2-plugin-extensions-opkg-tools_1.4_cortexa15hf-neon-vfpv4.ipk" , "", d)} \
    enigma2-plugin-extensions-oscamstatusview_1.0rc1_all.ipk \
    enigma2-plugin-extensions-piconmanager_2.2-20150329-r0_all.ipk \
    enigma2-plugin-extensions-piconsupdater_0.3.7_mips32el.ipk \
    enigma2-plugin-extensions-picturecenterfs_8.33_all.ipk \
    enigma2-plugin-extensions-planerfs_9.36_all.ipk \
    enigma2-plugin-extensions-pluginspanel_1.0_r01_all.ipk \
    enigma2-plugin-extensions-pluginupdater_0.1r2_all.ipk \
    enigma2-plugin-extensions-pravoslavietv_0.2_all.ipk \
    enigma2-plugin-extensions-pzyemail_0.8-20150614_all.ipk \
    enigma2-plugin-extensions-screengrabber_2.5_all.ipk \
    enigma2-plugin-extensions-screensaver_5.7.1_all.ipk \
    enigma2-plugin-extensions-serienrecorder_3.8.0_all.ipk \
    enigma2-plugin-extensions-sherlock_5.01r1_mipsel.ipk \
    enigma2-plugin-extensions-skyrecorder_1.7.2_all.ipk \
    enigma2-plugin-extensions-songs-to_0.1_mips32el.ipk \
    enigma2-plugin-extensions-spiegelonline_2.8rc15_all.ipk \
    enigma2-plugin-extensions-spinnerselector_2.3_all.ipk \
    enigma2-plugin-extensions-sport1ticker_1.3_all.ipk \
    enigma2-plugin-extensions-sundtekcontrolcenter_20180605-2_all.ipk \
    enigma2-plugin-extensions-tectimetv_3.1_all.ipk \
    enigma2-plugin-extensions-thetvdb_0.7-20120607-r3_mips32el.ipk \
    enigma2-plugin-extensions-timfs_2.32_all.ipk \
    enigma2-plugin-extensions-tmbd_8.3_all.ipk \
    enigma2-plugin-extensions-tmdb_0.7-r2_all.ipk \
    enigma2-plugin-extensions-translator_1.1rc1_all.ipk \
    enigma2-plugin-extensions-transmission_2.92-r2_all.ipk \
    enigma2-plugin-extensions-tsmedia_13.1-r1_all.ipk \
    enigma2-plugin-extensions-tstube_1.2_all.ipk \
    enigma2-plugin-extensions-tvspielfilm_6.6rc4_all.ipk \
    enigma2-plugin-extensions-verkehrsinfo_1.2rc3_all.ipk \
    enigma2-plugin-extensions-vcs_2.7_all.ipk \
    enigma2-plugin-extensions-vhannibalautosettings_1.1_mips32el.ipk \
    enigma2-plugin-extensions-vuplusforum_0.8rc2_all.ipk \
    enigma2-plugin-extensions-webradiofs_17.26_all.ipk \
    enigma2-plugin-extensions-wikipedia_2.3rc3_all.ipk \
    enigma2-plugin-extensions-wwech_1.03_all.ipk \
    enigma2-plugin-extensions-xbmcwetter_1.4rc1_all.ipk \
    enigma2-plugin-extensions-zdfnewmediathek_2.1rc15_all.ipk \
    enigma2-plugin-systemplugins-bouquetsprotection_0.2-rc1_all.ipk \
    enigma2-plugin-systemplugins-channelselectionnavigator_1.5_mipsel.ipk \
    enigma2-plugin-systemplugins-extnumberzap_1.20_all.ipk \
    enigma2-plugin-systemplugins-recinfobar_1.22rc2_mips32el.ipk \
    enigma2-plugin-picons-snp-full.400x240-400x240.light.on.transparent_2017-08-30--10-28-13_all.ipk \
    enigma2-plugin-picons-srp-full.400x240-400x240.light.on.transparent_2017-08-30--10-28-13_all.ipk \
    "

#     Install any packages t_append =hat are only For this machines feed here, uncomment the line below and change as required
THIRDPARTY_MACHINE_PLUGINS_vuuno = " \
    enigma2-plugin-extensions-sdg-imagedownloader-v0.7-oe-2.0-vu-all.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_vuultimo = " \
    enigma2-plugin-extensions-sdg-imagedownloader-v0.7-oe-2.0-vu-all.ipk \
    "	
THIRDPARTY_MACHINE_PLUGINS_vusolo = " \
    enigma2-plugin-extensions-sdg-imagedownloader-v0.7-oe-2.0-vu-all.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_vuduo = " \
    enigma2-plugin-extensions-sdg-imagedownloader-v0.7-oe-2.0-vu-all.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_vusolo2 = " \
    enigma2-plugin-extensions-sdg-imagedownloader-v0.7-oe-2.0-vu-all.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_vuduo2 = " \
    enigma2-plugin-extensions-sdg-imagedownloader-v0.7-oe-2.0-vu-all.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_vusolose = " \
    enigma2-plugin-extensions-sdg-imagedownloader-v0.7-oe-2.0-vu-all.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_vuzero = " \
    enigma2-plugin-extensions-sdg-imagedownloader-v0.7-oe-2.0-vu-all.ipk \
    "	

THIRDPARTY_MACHINE_PLUGINS_et8000 = " \
    enigma2-plugin-systemplugins-autorebootET8000_1.3_mipsel.ipk \
     "

do_install() {
    [[ -e ${WORKDIR}/enigma2-plugin-extensions-mediaportal_all.ipk ]] && rm ${WORKDIR}/enigma2-plugin-extensions-mediaportal_all.ipk
    wget --unlink -q  https://sourceforge.mirrorservice.org/e/project/e2/e2-mediaportal/enigma2-plugin-extensions-mediaportal_all.ipk -P ${WORKDIR}/ \
    && cp --preserve=timestamps ${WORKDIR}/enigma2-plugin-extensions-mediaportal_all.ipk ${S}/ \
    || true
}

python populate_packages_prepend () {
    pkg  = ""
    pkgs = ""
    plugins = d.getVar('THIRDPARTY_PLUGINS', True)
    if d.getVar('THIRDPARTY_MACHINE_PLUGINS', True) is not None:
        plugins += d.getVar('THIRDPARTY_MACHINE_PLUGINS', True)
    if d.getVar('THIRDPARTY_EXTRA_PLUGINS', True) is not None:
        plugins += d.getVar('THIRDPARTY_EXTRA_PLUGINS', True)

    if plugins is not None:
        for package in plugins.split():
            pkg = package.split('_')[0]
            pkgs += pkg + " "
            d.setVar('ALLOW_EMPTY_' + pkg, '1')

    d.setVar('PACKAGES', pkgs)
}

do_deploy() {
    rm -rf ${DEPLOY_DIR_IPK}/3rdparty
    rm -rf ${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty
    install -d 0755 ${DEPLOY_DIR_IPK}/3rdparty
    install -d 0755 ${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty
    for i in ${THIRDPARTY_PLUGINS}; do
        if [ -f $i ]; then
            install -m 0644 $i ${DEPLOY_DIR_IPK}/3rdparty;
        fi
    done;
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

do_package_write_ipk() {
    :
}

addtask do_deploy before do_package_write after do_package_write_ipk
