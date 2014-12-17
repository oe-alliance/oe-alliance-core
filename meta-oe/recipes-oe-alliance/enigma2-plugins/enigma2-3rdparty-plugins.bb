SUMMARY = "3rd Party plugins for Enigma2"
MAINTAINER = "oe-alliance team"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

inherit gitpkgv deploy

DEPENDS = "tslib mpfr gmp"

SRCREV = "${AUTOREV}"
PV = "2.1+gitr${SRCPV}"
PKGV = "2.1+gitr${GITPKGV}"
PR = "r123"

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
    enigma2-plugin-extensions-sundtekcontrolcenter_current_all.ipk \
    enigma2-plugin-extensions-atmolightd_0.7-pre22_all.ipk \
    enigma2-plugin-extensions-autobouquets-e2_20141113r1_mips32el.ipk \
    enigma2-plugin-extensions-bildonline_3.1rc4_mips32el.ipk \
    enigma2-plugin-extensions-bluray_1.5c2_mips32el.ipk \
    enigma2-plugin-extensions-boblight-enigma2_0.8r7_mips32el.ipk \
    enigma2-plugin-extensions-camofs_8.59_all.ipk \
    enigma2-plugin-extensions-chefkoch_1.4c4_mips32el.ipk \
    enigma2-plugin-extensions-clevertanken_0.5rc1_mips32el.ipk \
    enigma2-plugin-extensions-csfd_10-00-20141119_all.ipk \
    enigma2-plugin-extensions-cyrussettings_1.0.0_all.ipk \
    enigma2-plugin-extensions-digitalfernsehen_1.0rc4_mips32el.ipk \
    enigma2-plugin-extensions-enigmalight_0.1r5-b12_all.ipk \
    enigma2-plugin-extensions-facebook_1.3rc1_mips32el.ipk \
    enigma2-plugin-extensions-filesearchfs_2.53_all.ipk \
    enigma2-plugin-extensions-focusonline_1.0rc4_mips32el.ipk \
    enigma2-plugin-extensions-fragmutti_0.2rc1_mips32el.ipk \
    enigma2-plugin-extensions-gmailreader-oe2.0_1.3_all.ipk \
    enigma2-plugin-extensions-googlenewsreader_1.0_mips32el.ipk \
    enigma2-plugin-extensions-hetweer_2.6_mips32el.ipk \
    enigma2-plugin-extensions-hdmitest_0.4_mipsel.ipk \
    enigma2-plugin-extensions-isettinge2-3.3.0-oe2.0-mips32el.ipk \
    enigma2-plugin-extensions-kicker_3.3rc11_mips32el.ipk \
    enigma2-plugin-extensions-kino_1.4rc3_mips32el.ipk \
    enigma2-plugin-extensions-kodidirect_1.0_r0_all.ipk \
    enigma2-plugin-extensions-livefootball-oe2.0_5.4_all.ipk \
    enigma2-plugin-extensions-mediainfo_2.5_all.ipk \
    enigma2-plugin-extensions-mediaportal_6.1.1_all.ipk \
    enigma2-plugin-extensions-msnwetter_0.6rc3_mips32el.ipk \
    enigma2-plugin-extensions-mtv_0.1_mips32el.ipk \
    enigma2-plugin-extensions-muzutv_1.5rc3_mips32el.ipk \
    enigma2-plugin-extensions-mediastream_3.0.5_all.ipk \
    enigma2-plugin-extensions-moviebrowser_3.5rc4_mips32el.ipk \
    enigma2-plugin-extensions-mp3browser_1.9c4_mips32el.ipk \
    enigma2-plugin-extensions-navibar_1.1.1_all.ipk \
    enigma2-plugin-extensions-opkg-tools_1.3_mipsel.ipk \
    enigma2-plugin-extensions-oscamstatusview_0.5_mips32el.ipk \
    enigma2-plugin-extensions-planerfs_6.45_all.ipk \
    enigma2-plugin-extensions-pluginspanel_1.0_r01_all.ipk \
    enigma2-plugin-extensions-piconmanager_0.1-20140820-r0_all.ipk \
    enigma2-plugin-extensions-picturecenterfs_6.03_all.ipk \
    enigma2-plugin-extensions-radiode_0.1_mips32el.ipk \
    enigma2-plugin-extensions-radioonline-oe2.0_1.0_all.ipk \
    enigma2-plugin-extensions-screensaver_5.6.9_all.ipk \
    enigma2-plugin-extensions-screengrabber_2.1a_mips32el.ipk \
    enigma2-plugin-extensions-seriesplugin_0.9_mips32el.ipk \
    enigma2-plugin-extensions-sherlock_5.01r1_mipsel.ipk \
    enigma2-plugin-extensions-skyrecorder_1.5.1_all.ipk \
    enigma2-plugin-extensions-songs-to_0.1_mips32el.ipk \
    enigma2-plugin-extensions-spiegelonline_2.5rc12_mips32el.ipk \
    enigma2-plugin-extensions-sportmax_1.2_mips32el.ipk \
    enigma2-plugin-extensions-subtitleplayer_3.28_mips32el.ipk \
    enigma2-plugin-extensions-spinnerselector_2.0r5_mips32el.ipk \
    enigma2-plugin-extensions-sport1ticker_0.4_all.ipk \
    enigma2-plugin-extensions-sportportal_2013_all.ipk \
    enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk \
    enigma2-plugin-extensions-thetvdb_0.7-20120607-r1_mips32el.ipk \
    enigma2-plugin-extensions-translator_0.9c2_mips32el.ipk \
    enigma2-plugin-extensions-transmission_2.83_mips32el.ipk \
    enigma2-plugin-extensions-tsmedia_7.9_full_all.ipk \
    enigma2-plugin-extensions-tvspielfilm_6.3rc5_mips32el.ipk \
    enigma2-plugin-extensions-verkehrsinfo_1.0rc1_mips32el.ipk \
    enigma2-plugin-extensions-vhannibalautosettings_1.1_mips32el.ipk \
    enigma2-plugin-extensions-vuplusforum_0.7rc3_mips32el.ipk \
    enigma2-plugin-extensions-webmedia_10.0_r02_oe2.0_all.ipk \
    enigma2-plugin-extensions-webradiofs_12.58_all.ipk \
    enigma2-plugin-extensions-wikipedia_2.2rc5_mips32el.ipk \
    enigma2-plugin-extensions-wwech_1.01_all.ipk \
    enigma2-plugin-extensions-xbmcaddons_8.1_r0_all.ipk \
    enigma2-plugin-extensions-xbmcwetter_1.3rc3_mips32el.ipk \
    enigma2-plugin-extensions-yampmusicplayer_2.1.1-2012-09-23_mipsel.ipk \
    enigma2-plugin-extensions-zdfnewmediathek_1.7rc5_mips32el.ipk \
    enigma2-plugin-systemplugins-bouquetsprotection_0.2-rc1_all.ipk \
    enigma2-plugin-systemplugins-recordinfobar_1.0-rc15_mipsel.ipk \
    enigma2-plugin-picons-tv-ocram.hd.black-white_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.hd.blue-black_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.hd.reflection-black_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.hd.transparent-black_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.hd.transparent-white_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.hd.white-black_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.sd.black-white_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.sd.blue-black_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.sd.reflection-black_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.sd.transparent-black_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.sd.transparent-white_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.sd.white-black_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.shd.black-white_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.shd.blue-black_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.shd.reflection-black_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.shd.transparent-black_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.shd.transparent-black-nopadding_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.shd.transparent-white_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.shd.transparent-white-nopadding_2014-11-01--10-28-46_all.ipk \
    enigma2-plugin-picons-tv-ocram.shd.white-black_2014-11-01--10-28-46_all.ipk \
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
THIRDPARTY_MACHINE_PLUGINS_et4x00 = " \
    enigma2-plugin-extensions-newxtrend-hbbtv_2.5_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_et6x00 = " \
    enigma2-plugin-extensions-et-webbrowser_2.0.0-r0_et6x00.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_et7x00 = " \
    enigma2-plugin-extensions-newxtrend-hbbtv_2.5_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_et8000 = " \
    enigma2-plugin-extensions-newxtrend-hbbtv_2.5_mips32el.ipk \
     "
THIRDPARTY_MACHINE_PLUGINS_et8500 = " \
    enigma2-plugin-extensions-newxtrend-hbbtv_2.5_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_et9x00 = " \
    enigma2-plugin-extensions-et-webbrowser_2.0.0-r0_et9x00.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_et10000 = " \
    enigma2-plugin-extensions-newxtrend-hbbtv_2.5_mips32el.ipk \
     "
THIRDPARTY_MACHINE_PLUGINS_e3hd = " \
    enigma2-plugin-extensions-hbbtv_4.1_E3HD_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_odinm7 = " \
    enigma2-plugin-extensions-hbbtv_4.1_ODIN_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_odinm7s = " \
    enigma2-plugin-extensions-hbbtv_4.1_ODIN_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_odinm7c = " \
    enigma2-plugin-extensions-hbbtv_4.1_ODIN_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_inihdp = " \
    enigma2-plugin-extensions-hbbtv_1.1-INI_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_inihdx = " \
    enigma2-plugin-extensions-hbbtv_1.1-INI_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_inihde = " \
    enigma2-plugin-extensions-hbbtv_1.1-INI_mips32el.ipk \
    "
THIRDPARTY_MACHINE_PLUGINS_inihde2 = " \
    enigma2-plugin-extensions-hbbtv_1.1-INI_mips32el.ipk \
    "    
    
do_install() {
}

python populate_packages_prepend () {
    p = ""
    plugins = bb.data.getVar('THIRDPARTY_PLUGINS', d, 1)
    if bb.data.getVar('THIRDPARTY_MACHINE_PLUGINS', d, 1) is not None:
        plugins += bb.data.getVar('THIRDPARTY_MACHINE_PLUGINS', d, 1)
    if bb.data.getVar('THIRDPARTY_EXTRA_PLUGINS', d, 1) is not None:
        plugins += bb.data.getVar('THIRDPARTY_EXTRA_PLUGINS', d, 1)

    if plugins is not None:
        for package in plugins.split():
            p += package.split('_')[0] + " "

    bb.data.setVar('PACKAGES', p, d)
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
