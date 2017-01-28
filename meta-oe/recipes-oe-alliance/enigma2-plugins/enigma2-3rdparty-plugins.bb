SUMMARY = "3rd Party plugins for Enigma2"
MAINTAINER = "oe-alliance team"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

inherit gitpkgv deploy

DEPENDS = "tslib mpfr gmp"

SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+gitr${SRCPV}"
PKGV = "${GITPKGVTAG}"
PR = "r12"

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

THIRDPARTY_PLUGINS = " \
    enigma2-plugin-extensions-atmolightd_0.7-pre22_all.ipk \
    enigma2-plugin-extensions-bluray_1.6rc1_all.ipk \
    enigma2-plugin-extensions-boblight-enigma2_0.8r8_mips32el.ipk \
    enigma2-plugin-extensions-camofs_9.19_all.ipk \
    enigma2-plugin-extensions-chefkoch_1.5rc2_all.ipk \
    enigma2-plugin-extensions-clevertanken_0.8rc1_all.ipk \
    enigma2-plugin-extensions-csfd_11-55-20160929_all.ipk \
    enigma2-plugin-extensions-customsubservices_0.1.2_all.ipk \
    enigma2-plugin-extensions-cyrussettings_1.0.0_all.ipk \
    enigma2-plugin-extensions-digitalfernsehen_1.2rc1_all.ipk \
    enigma2-plugin-extensions-dvrproviderplayer_1.14_all.ipk \
    enigma2-plugin-extensions-ehue_0.2-r0_all.ipk \
    enigma2-plugin-extensions-enigmalight_0.2-rc1_all.ipk \
    enigma2-plugin-extensions-facebook_1.4_mips32el.ipk \
    enigma2-plugin-extensions-filesearchfs_2.53_all.ipk \
    enigma2-plugin-extensions-focusonline_1.1rc1_all.ipk \
    enigma2-plugin-extensions-fragmutti_1.0rc1_all.ipk \
    enigma2-plugin-extensions-gmailreader-oe2.0_1.3_all.ipk \
    enigma2-plugin-extensions-googlenewsreader_1.0_mips32el.ipk \
    enigma2-plugin-extensions-hdmitest_0.4_mipsel.ipk \
    enigma2-plugin-extensions-hetweer_3.9rc1_all.ipk \
    enigma2-plugin-extensions-iptvbouquet_1.0_r0_all.ipk \
    ${@bb.utils.contains("TARGET_ARCH", "sh4", "enigma2-plugin-extensions-isettinge2-3.3.8_sh4.ipk" , "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "enigma2-plugin-extensions-isettinge2-3.3.8_mips32el.ipk" , "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "arm", "enigma2-plugin-extensions-isettinge2-3.3.8_armv7ahf-neon.ipk" , "", d)} \
    enigma2-plugin-extensions-kicker_3.5rc1_all.ipk \
    enigma2-plugin-extensions-kino_1.7rc1_all.ipk \
    enigma2-plugin-extensions-livefootball_6.1_all.ipk \
    enigma2-plugin-extensions-mediainfo_3.0.0_all.ipk \
    enigma2-plugin-extensions-mediaportal_7.7.0_all.ipk \
    enigma2-plugin-extensions-mediaportal-wallicons.ipk \
    enigma2-plugin-extensions-moviebrowser_3.7rc1_all.ipk \
    enigma2-plugin-extensions-mp3browser_2.0rc1_all.ipk \
    enigma2-plugin-extensions-netspeedtest_1.0rc1_all.ipk \
    ${@bb.utils.contains('MACHINE_BRAND', 'Vu+', '' , 'enigma2-plugin-extensions-openopera_1.0-r0_mips32el.ipk', d)} \
    enigma2-plugin-extensions-opkg-tools_1.3_mipsel.ipk \
    enigma2-plugin-extensions-oscamstatusview_1.0rc1_all.ipk \
    enigma2-plugin-extensions-piconmanager_2.2-20150329-r0_all.ipk \
    enigma2-plugin-extensions-piconsupdater_0.3.6_mips32el.ipk \
    enigma2-plugin-extensions-picturecenterfs_7.37_all.ipk \
    enigma2-plugin-extensions-planerfs_6.61_all.ipk \
    enigma2-plugin-extensions-pluginspanel_1.0_r01_all.ipk \
    enigma2-plugin-extensions-pluginupdater_0.1r2_all.ipk \
    enigma2-plugin-extensions-pravoslavietv_0.2_all.ipk \
    enigma2-plugin-extensions-pzyemail_0.8-20150614_all.ipk \
    enigma2-plugin-extensions-screengrabber_2.5_all.ipk \
    enigma2-plugin-extensions-screensaver_5.7.1_all.ipk \
    enigma2-plugin-extensions-sherlock_5.01r1_mipsel.ipk \
    enigma2-plugin-extensions-skyrecorder_1.6.1_all.ipk \
    enigma2-plugin-extensions-songs-to_0.1_mips32el.ipk \
    enigma2-plugin-extensions-spiegelonline_2.8rc2_all.ipk \
    enigma2-plugin-extensions-spinnerselector_2.1rc7_mips32el.ipk \
    enigma2-plugin-extensions-sport1ticker_1.3_all.ipk \
    enigma2-plugin-extensions-sportportal_2013_all.ipk \
    enigma2-plugin-extensions-sundtekcontrolcenter_20161213-2_all.ipk \
    enigma2-plugin-extensions-tectimetv_3.1_all.ipk \
    enigma2-plugin-extensions-thetvdb_0.7-20120607-r2_mips32el.ipk \
    enigma2-plugin-extensions-tmbd_7.8_all.ipk \
    enigma2-plugin-extensions-translator_1.1rc1_all.ipk \
    enigma2-plugin-extensions-transmission_2.84_all.ipk \
    enigma2-plugin-extensions-tsmedia_11.5_all.ipk \
    enigma2-plugin-extensions-tstube_1.2_all.ipk \
    enigma2-plugin-extensions-tvspielfilm_6.5rc3_all.ipk \
    enigma2-plugin-extensions-verkehrsinfo_1.2rc1_all.ipk \
    enigma2-plugin-extensions-vcs_2.4_all.ipk \
    enigma2-plugin-extensions-vhannibalautosettings_1.1_mips32el.ipk \
    enigma2-plugin-extensions-vuplusforum_0.8rc2_all.ipk \
    enigma2-plugin-extensions-webmedia_14.0_r0_all.ipk \
    enigma2-plugin-extensions-webradiofs_14.83_all.ipk \
    enigma2-plugin-extensions-wikipedia_2.3rc1_all.ipk \
    enigma2-plugin-extensions-wwech_1.03_all.ipk \
    enigma2-plugin-extensions-xbmcaddons_8.1_r0_all.ipk \
    enigma2-plugin-extensions-xbmcwetter_1.4rc1_all.ipk \
    enigma2-plugin-extensions-zdfnewmediathek_2.1rc2_all.ipk \
    enigma2-plugin-systemplugins-bouquetsprotection_0.2-rc1_all.ipk \
    enigma2-plugin-systemplugins-channelselectionnavigator_1.2_mipsel.ipk \
    enigma2-plugin-systemplugins-extnumberzap_1.17_all.ipk \
    enigma2-plugin-systemplugins-recinfobar_1.22rc2_mips32el.ipk \
    enigma2-plugin-picons-snp-full.400x240-400x240.light.on.transparent_2016-12-22--08-37-51_all.ipk \
    enigma2-plugin-picons-srp-full.400x240-400x240.light.on.transparent_2016-12-22--08-37-51_all.ipk \
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

THIRDPARTY_MACHINE_PLUGINS_et7x00 = " \
    enigma2-plugin-extensions-newxtrend-hbbtv_4.0-r0_et7x00.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_et8000 = " \
    enigma2-plugin-extensions-newxtrend-hbbtv_4.0-r0_et8000.ipk \
    enigma2-plugin-systemplugins-autorebootET8000_1.3_mipsel.ipk \
     "
THIRDPARTY_MACHINE_PLUGINS_et8500 = " \
    enigma2-plugin-extensions-newxtrend-hbbtv_4.0-r0_et8500.ipk \
    "

THIRDPARTY_MACHINE_PLUGINS_et10000 = " \
    enigma2-plugin-extensions-newxtrend-hbbtv_4.0-r0_et10000.ipk \
     "
THIRDPARTY_MACHINE_PLUGINS_ultramini = " \
    ${@bb.utils.contains('MACHINEBUILD', 'et7x00mini', 'enigma2-plugin-extensions-hbbtv-et7x00mini_2.0-r0_et7x00mini.ipk' , '', d)} \
    ${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcc', 'enigma2-plugin-extensions-hbbtv-xpeedc_2.0-r0_xpeedlxcc.ipk' , '', d)} \
    ${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcs2', 'enigma2-plugin-extensions-hbbtv-xpeedlxcs2_2.0-r0_xpeedlxcs2.ipk' , '', d)} \
    "

THIRDPARTY_MACHINE_PLUGINS_g300 = " \
    ${@bb.utils.contains('MACHINEBUILD', 'sf3038', 'enigma2-plugin-extensions-hbbtv-octagon_1.0_mips32el.ipk' , '', d)} \
    ${@bb.utils.contains('MACHINEBUILD', 'mbtwinplus', 'enigma2-plugin-extensions-hbbtv-miracle_1.0_mips32el.ipk' , '', d)} \
    "

do_install[noexec] = "1"
do_package_write_ipk[noexec] = "1"

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

addtask do_deploy before do_package_write after do_package_write_ipk
