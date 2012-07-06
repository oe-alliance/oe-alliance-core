DESCRIPTION = "3rd-Party plugins for Enigma2"
MAINTAINER = "oe-alliance team"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r2"

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

DEPENDS = "enigma2"

do_install_vuuno() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-picturecenterfs_3.30_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_vuultimo() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-picturecenterfs_3.30_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-systemplugins-pipservicerelation_experimental-git20111106-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_vusolo() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-clearmem_1.07_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-picturecenterfs_3.30_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_vuduo() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-clearmem_1.07_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-picturecenterfs_3.30_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-systemplugins-pipservicerelation_experimental-git20111106-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_et5x00() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-clearmem_1.07_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-picturecenterfs_3.30_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_et6x00() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-webbrowser_1.5.3-r0_et6x00.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-picturecenterfs_3.30_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-weblinks-basic_1.3-r0_et6x00.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-weblinks-games_1.0-r0_et6x00.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_et9x00() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-portal_1.1-r0_et9x00.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-webbrowser_1.5.3-r0_et9x00.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-picturecenterfs_3.30_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-systemplugins-pipservicerelation_experimental-git20111106-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-weblinks-basic_1.3-r0_et9x00.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-weblinks-games_1.0-r0_et9x00.ipk ${DEPLOY_DIR_IPK}/3rdparty}
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty

do_install_tmtwin() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-newtube_0.1-r0_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-systemplugins-pipservicerelation_experimental-git20111106-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_gb800se() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-newtube_0.1-r0_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-systemplugins-pipservicerelation_experimental-git20111106-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_gb800ue() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-newtube_0.1-r0_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-systemplugins-pipservicerelation_experimental-git20111106-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_gb800solo() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-newtube_0.1-r0_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-systemplugins-pipservicerelation_experimental-git20111106-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_gbquad() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-newtube_0.1-r0_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-systemplugins-pipservicerelation_experimental-git20111106-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_odinm9() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-newtube_0.1-r0_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-systemplugins-pipservicerelation_experimental-git20111106-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_ventonhdx() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-newtube_0.1-r0_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-systemplugins-pipservicerelation_experimental-git20111106-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_dm8000() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-newtube_0.1-r0_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-systemplugins-pipservicerelation_experimental-git20111106-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_dm7020hd() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-newtube_0.1-r0_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-systemplugins-pipservicerelation_experimental-git20111106-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_dm500hd() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-newtube_0.1-r0_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-systemplugins-pipservicerelation_experimental-git20111106-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

do_install_dm80se() {
	install -d 0644 ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-atmolightd_0.4a_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-camofs_6.16_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-dreamexplorer_7.3_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-livestream_1.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-et-openug_1.1-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-livefootball_3.6_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-meteoviewer_1.68_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-newtube_0.1-r0_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-subtitleplayer_3.02_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webmedia_6.0-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-extensions-webradiofs_7.73_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/enigma2-plugin-systemplugins-pipservicerelation_experimental-git20111106-r0_mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty
	install -m 0644 ${S}/spinner-selector_2.0_mipsel ${DEPLOY_DIR_IPK}/3rdparty
}

