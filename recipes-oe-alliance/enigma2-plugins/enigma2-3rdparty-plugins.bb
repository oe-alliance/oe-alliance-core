DESCRIPTION = "3rd Party plugins for Enigma2"
MAINTAINER = "oe-alliance team"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PV = "2.0"
PR = "r38"

SRC_URI="git://github.com/oe-alliance/3rdparty-plugins.git;protocol=git"

EXTRA_OECONF = " \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit autotools deploy

S = "${WORKDIR}/git"

DEPENDS = "enigma2"

THIRDPARTY_PLUGINS = " \
	enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk \
	enigma2-plugin-extensions-aspectratioswitch_0.8_mips32el.ipk \
	enigma2-plugin-extensions-airplayer_0.3.9_mips32el.ipk \
	enigma2-plugin-extensions-atmolightd_0.7-pre22_all.ipk \
	enigma2-plugin-extensions-autobouquets-e2_201308251700-r1_mips32el.ipk \
	enigma2-plugin-extensions-bildonline_2.9rc5_mips32el.ipk \
	enigma2-plugin-extensions-bluray_1.1rc4_mips32el.ipk \
	enigma2-plugin-extensions-boblight-enigma2_0.7r7_Beta_mips32el.ipk \
	enigma2-plugin-extensions-buyukbangpanel_1.3.2_all.ipk \
	enigma2-plugin-extensions-camofs_7.62_all.ipk \
	enigma2-plugin-extensions-chefkoch_1.1rc1_mips32el.ipk \
	enigma2-plugin-extensions-csfd_9_35-20130615_all.ipk \
	enigma2-plugin-extensions-cubic_streamer_0.7.3_mips32el.ipk \
	enigma2-plugin-extensions-digitalfernsehen_0.8rc1_mips32el.ipk \
	enigma2-plugin-extensions-dreamexplorer_7.4-r1_mips32el.ipk \
	enigma2-plugin-extensions-facebook_0.6_mips32el.ipk \
	enigma2-plugin-extensions-filesearchfs_2.53_all.ipk \
	enigma2-plugin-extensions-focusonline_0.7rc5_mips32el.ipk \
	enigma2-plugin-extensions-glasssysutil_5.37_all.ipk \
	enigma2-plugin-extensions-gmailreader-oe2.0_1.3_all.ipk \
	enigma2-plugin-extensions-googlenewsreader_1.0_mips32el.ipk \
	enigma2-plugin-extensions-heizoelpreisefs_3.70_all.ipk \
	enigma2-plugin-extensions-hetweer_1.2_all.ipk \
	enigma2-plugin-extensions-hdmitest_0.4_mipsel.ipk \
	enigma2-plugin-extensions-kicker_2.9rc6_mips32el.ipk \
	enigma2-plugin-extensions-kino_1.1rc2_mips32el.ipk \
	enigma2-plugin-systemplugins-lcnscanner_1.0-r4_mips32el.ipk \
	enigma2-plugin-extensions-livefootball_4.4_mips32el.ipk \
	enigma2-plugin-extensions-mediainfo_0.6_r04_all.ipk \
	enigma2-plugin-extensions-mediatomb_0.12.1-r3_mips32el.ipk \
	enigma2-plugin-extensions-msnwetter_0.6rc3_mips32el.ipk \
	enigma2-plugin-extensions-mtv_0.1_mips32el.ipk \
	enigma2-plugin-extensions-moviebrowser_2.1rc7_mips32el.ipk \
	enigma2-plugin-extensions-mp3browser_0.5rc1_mips32el.ipk \
	enigma2-plugin-extensions-navibar_1.1.1_all.ipk \
	enigma2-plugin-extensions-newtube_0.1a-r0_all.ipk \
	enigma2-plugin-extensions-opkg-tools_1.3_mipsel.ipk \
	enigma2-plugin-extensions-planerfs_4.84_all.ipk \
	enigma2-plugin-extensions-pluginspanel_1.0_r01_all.ipk \
	enigma2-plugin-extensions-picturecenterfs_3.71_all.ipk \
	enigma2-plugin-extensions-radiode_0.1_mips32el.ipk \
	enigma2-plugin-extensions-radioonline-oe2.0_1.0_all.ipk \
	enigma2-plugin-extensions-screensaver_5.6.9_all.ipk \
	enigma2-plugin-extensions-screengrabber_2.1a_mips32el.ipk \
	enigma2-plugin-extensions-seriesplugin_0.8.7.4_mips32el.ipk \
	enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk \
	enigma2-plugin-extensions-skyrecorder_1.2.4r1_all.ipk \
	enigma2-plugin-extensions-songs-to_0.1_mips32el.ipk \
	enigma2-plugin-extensions-spiegelonline_2.3rc1_mips32el.ipk \
	enigma2-plugin-extensions-sportmax_1.2_mips32el.ipk \
	enigma2-plugin-extensions-subtitleplayer_3.20_mipsel.ipk \
	enigma2-plugin-extensions-spinnerselector_2.0r5_mips32el.ipk \
	enigma2-plugin-extensions-sport1ticker_0.4_all.ipk \
	enigma2-plugin-extensions-sportportal_2013_all.ipk \
	enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk \
	enigma2-plugin-extensions-thetvdb_0.7-20120607-r1_mips32el.ipk \
	enigma2-plugin-extensions-tmdbinfo_1.0-20120508-r2_mips32el.ipk \
	enigma2-plugin-extensions-translator_0.5rc3_mips32el.ipk \
	enigma2-plugin-extensions-transmission_2.76-r13884_mips32el.ipk \
	enigma2-plugin-extensions-tsmedia-oe2.0_3.9_all.ipk \
	enigma2-plugin-extensions-tvspielfilm_5.6rc3_mips32el.ipk \
	enigma2-plugin-extensions-vcs_1.0-rc5_all.ipk \
	enigma2-plugin-extensions-verkehrsinfo_0.6rc1_mips32el.ipk \
	enigma2-plugin-extensions-vuplusforum_0.6rc2_mips32el.ipk \
	enigma2-plugin-extensions-webmedia_10.0_r01_oe2.0_all.ipk \
	enigma2-plugin-extensions-webradiofs_11.11_all.ipk \
	enigma2-plugin-extensions-wikipedia_1.8rc3_mips32el.ipk \
	enigma2-plugin-extensions-yampmusicplayer_2.1.1-2012-09-23_mipsel.ipk \
	enigma2-plugin-extensions-zdfnewmediathek_1.3rc4_mips32el.ipk \
	enigma2-plugin-systemplugins-recordinfobar_1.0-rc15_mipsel.ipk \
	"

# 	Install any packages t_append =hat are only For this machines feed here, uncomment the line below and change as required
THIRDPARTY_MACHINE_PLUGINS_vuuno = " \
	enigma2-plugin-extensions-sdg-imagedownloader-v0.5-oe-2.0-vu-all.ipk \
	"
THIRDPARTY_MACHINE_PLUGINS_vuultimo = " \
	enigma2-plugin-extensions-sdg-imagedownloader-v0.5-oe-2.0-vu-all.ipk \
	"
THIRDPARTY_MACHINE_PLUGINS_vusolo = " \
	enigma2-plugin-extensions-sdg-imagedownloader-v0.5-oe-2.0-vu-all.ipk \
	"
THIRDPARTY_MACHINE_PLUGINS_vuduo = " \
	enigma2-plugin-extensions-sdg-imagedownloader-v0.5-oe-2.0-vu-all.ipk \
	"
THIRDPARTY_MACHINE_PLUGINS_vusolo2 = " \
	enigma2-plugin-extensions-sdg-imagedownloader-v0.5-oe-2.0-vu-all.ipk \
	"
THIRDPARTY_MACHINE_PLUGINS_vuduo2 = " \
	enigma2-plugin-extensions-sdg-imagedownloader-v0.5-oe-2.0-vu-all.ipk \
	"
THIRDPARTY_MACHINE_PLUGINS_et4x00 = " \
	enigma2-plugin-extensions-et-webbrowser_1.6.1-r1_et4x00.ipk \
	enigma2-plugin-weblinks-basic_1.3-r0_et4x00.ipk \
	enigma2-plugin-weblinks-games_1.0-r0_et4x00.ipk \
	"
THIRDPARTY_MACHINE_PLUGINS_et5x00 = " \
	"
THIRDPARTY_MACHINE_PLUGINS_et6x00 = " \
	enigma2-plugin-extensions-et-webbrowser_1.6.2-r1_et6x00.ipk \
	enigma2-plugin-weblinks-basic_1.3-r0_et6x00.ipk \
	enigma2-plugin-weblinks-games_1.0-r0_et6x00.ipk \
	"
THIRDPARTY_MACHINE_PLUGINS_et9x00 = " \
	enigma2-plugin-extensions-et-webbrowser_1.6.1-r1_et9x00.ipk \
	enigma2-plugin-weblinks-basic_1.3-r0_et9x00.ipk \
	enigma2-plugin-weblinks-games_1.0-r0_et9x00.ipk \
	"

THIRDPARTY_MACHINE_PLUGINS_e3hd = " \
	enigma2-plugin-extensions-opera_1.01_mips32el.ipk \
	"

THIRDPARTY_MACHINE_PLUGINS_dm8000 = " \
	enigma2-plugin-extensions-dflash_9.4.2-r02_all.ipk \
	"
THIRDPARTY_MACHINE_PLUGINS_dm7020hd = " \
	enigma2-plugin-extensions-dflash_9.4.2-r02_all.ipk \
	"
THIRDPARTY_MACHINE_PLUGINS_dm500hd = " \
	enigma2-plugin-extensions-dflash_9.4.2-r02_all.ipk \
	"
THIRDPARTY_MACHINE_PLUGINS_dm800se = " \
	enigma2-plugin-extensions-dflash_9.4.2-r02_all.ipk \
	"
THIRDPARTY_MACHINE_PLUGINS_dm7020hdv2 = " \
	enigma2-plugin-extensions-dflash_9.4.2-r02_all.ipk \
	"
THIRDPARTY_MACHINE_PLUGINS_dm500hdv2 = " \
	enigma2-plugin-extensions-dflash_9.4.2-r02_all.ipk \
	"
THIRDPARTY_MACHINE_PLUGINS_dm800sev2 = " \
	enigma2-plugin-extensions-dflash_9.4.2-r02_all.ipk \
	"
THIRDPARTY_MACHINE_PLUGINS_dm800 = " \
	enigma2-plugin-extensions-dflash_9.4.2-r02_all.ipk \
	"

do_install() {
}

do_deploy() {
	install -d 0755 ${WORKDIR}/deploy-ipks/3rdparty
	install -d 0755 ${WORKDIR}/deploy-ipks/${MACHINE}_3rdparty
	for i in ${THIRDPARTY_PLUGINS}; do
		if [ -f $i ]; then
			install -m 0644 $i ${WORKDIR}/deploy-ipks/3rdparty;
		fi
	done;
	for i in ${THIRDPARTY_MACHINE_PLUGINS}; do
		if [ -f $i ]; then
			install -m 0644 $i ${WORKDIR}/deploy-ipks/${MACHINE}_3rdparty;
		fi
	done;
	for i in ${THIRDPARTY_EXTRA_PLUGINS}; do
		if [ -f $i ]; then
			install -m 0644 $i ${WORKDIR}/deploy-ipks/3rdparty;
		fi
	done;
}

do_chmod() {
	pkgdir=${DEPLOY_DIR_IPK}/3rdparty
	if [ -e $pkgdir ]; then
		chmod 0755 $pkgdir
	fi
	pkgdir=${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty
	if [ -e $pkgdir ]; then
		chmod 0755 $pkgdir
	fi
}

addtask deploy before do_build after do_install
addtask chmod before do_build after do_package_write_ipk

