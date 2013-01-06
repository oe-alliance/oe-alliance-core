DESCRIPTION = "3rd Party plugins for Enigma2"
MAINTAINER = "oe-alliance team"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r166"

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

DEPENDS = "enigma2"
do_install() {
}

do_deploy() {
	install -d 0755 ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-1channel_1.4_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-appletrailer_0.1_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-airplayer_0.3.2b4_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-atmolightd_0.7-pre1_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-boblight-enigma2_0.5r15_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-buyukbangpanel_1.3.2_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-dvb-sundtek.controlcenter_1.0-20110318-r2_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-camofs_6.43_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-cubic_streamer_0.6.9_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-dreamexplorer_7.4-r1_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-googlenewsreader_1.0_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-hetweer_1.2_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-hdmitest_0.4_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-icefilms_03-01-2013_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-livefootball_4.3_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-newtube_0.1a-r0_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-opkg-tools_1.2_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-pluginspanel_1.0_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-picturecenterfs_3.51_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-systemplugins-recordinfobar_1.0-rc15_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-screensaver_5.6.9_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-screengrabber_2.1a_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-sherlock-vuplus_5.01_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-subtitleplayer_3.20_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-spinner-selector_2.0_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-systemtools-for-ppanels_0.83_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-transmission_2.75-r13682_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-thetvdb_0.7-20120607-r1_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-tmdbinfo_1.0-20120508-r2_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-vcs_1.0-rc5_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-webmedia_9.1_r3_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-webradiofs_9.10_all.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-wikipedia_0.9_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-yampmusicplayer_1.6b-2012-05-23_mipsel.ipk ${WORKDIR}/deploy-ipks/3rdparty
}

do_deploy_append_vuuno() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}
do_deploy_append_vuultimo() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}
do_deploy_append_vusolo() {
# 	Install any packages that are only For this machines feed here,
}
do_deploy_append_vuduo() {
# 	Install any packages that are only For this machines feed here,
}

do_deploy_append_et4x00() {
# 	Install any packages that are only For this machines feed here,
	install -m 0644 enigma2-plugin-extensions-et-portal_2.3_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-et-webbrowser_1.6.1-r0_et4x00.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-weblinks-basic_1.3-r0_et4x00.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-weblinks-games_1.0-r0_et4x00.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
do_deploy_append_et5x00() {
# 	Install any packages that are only For this machines feed here,
	install -m 0644 enigma2-plugin-extensions-et-portal_2.3_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
do_deploy_append_et6x00() {
# 	Install any packages that are only For this machines feed here,
	install -m 0644 enigma2-plugin-extensions-et-portal_2.3_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-et-webbrowser_1.6.1-r0_et6x00.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-weblinks-basic_1.3-r0_et6x00.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-weblinks-games_1.0-r0_et6x00.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
do_deploy_append_et9x00() {
# 	Install any packages that are only For this machines feed here,
	install -m 0644 enigma2-plugin-extensions-et-portal_2.3_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-et-webbrowser_1.6.1-r0_et9x00.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-weblinks-basic_1.3-r0_et9x00.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-weblinks-games_1.0-r0_et9x00.ipk ${WORKDIR}/deploy-ipks/3rdparty}
}

do_deploy_append_tmtwin() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}
do_deploy_append_tm2t() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}
do_deploy_append_tmsingle() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}

do_deploy_append_gb800se() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}
do_deploy_append_gb800ue() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}
do_deploy_append_gb800solo() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}
do_deploy_append_gbquad() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}

do_deploy_append_odinm9() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}

do_deploy_append_ventonhdx() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}
do_deploy_append_ventonhde() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}

do_deploy_append_dm8000() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}
do_deploy_append_dm7020hd() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}
do_deploy_append_dm500hd() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}
do_deploy_append_dm800se() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}
do_deploy_append_dm800() {
# 	Install any packages that are only For this machines feed here, uncomment the line below and change as required
# 	install -m 0644 enigma2-plugin-private-menuluxipkg_4.1b_all.ipk ${WORKDIR}/deploy-ipks/private
}

addtask deploy before do_build after do_install
addtask chmod before do_build after do_package_write_ipk

do_chmod() {
	pkgdir=${DEPLOY_DIR_IPK}/3rdparty
	if [ -e $pkgdir ]; then
		chmod 0755 $pkgdir
	fi
}
