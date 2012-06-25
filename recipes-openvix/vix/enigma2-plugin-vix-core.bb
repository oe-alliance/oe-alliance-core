DESCRIPTION = "Vix Core"
MAINTAINER = "Andy Blackburn"

require conf/license/openvix-gplv2.inc

PACKAGE_ARCH := "${MACHINE_ARCH}"

DEPENDS = "enigma2 python mtd-utils"
RDEPENDS = "mtd-utils"

RCONFLICTS_${PN} = "settings-autorestore"
RREPLACES_${PN} = "settings-autorestore"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
PR = "r2"

SRC_URI="git://git.assembla.com/openvix.10.git;protocol=git \
	http://enigma2.world-of-satellite.com/git-extras/vix-bootlogos.tgz"

S = "${WORKDIR}/git"

inherit autotools

EXTRA_OECONF = "\
	--with-po --with-libsdl=no --with-boxtype=${MACHINE} \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
"

PACKAGES =+ "${PN}-src"
PACKAGES =+ "${PN}-po"
FILES_${PN} = "/etc /usr/lib"
FILES_${PN}-dbg = "/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/.debug/"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/*.py"
FILES_${PN}-po = "${datadir}/enigma2/po/*.po"

# remove unused .pyc files
do_install_append() {
	find ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/ -name '*.pyc' -exec rm {} \;
}

do_install_append_vuuno() {
	install -m 0644 ${WORKDIR}/${MACHINE}/splash_cfe_auto.bin ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/splash_cfe_auto.bin
}
do_install_append_vuultimo() {
	install -m 0644 ${WORKDIR}/${MACHINE}/splash_cfe_auto.bin ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/splash_cfe_auto.bin
}
do_install_append_et5x00() {
	install -m 0644 ${WORKDIR}/${MACHINE}/splash.bin ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/splash.bin
}
do_install_append_et6x00() {
	install -m 0644 ${WORKDIR}/${MACHINE}/splash.bin ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/splash.bin
}
do_install_append_et9x00() {
	install -m 0644 ${WORKDIR}/${MACHINE}/splash.bin ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/splash.bin
}
do_install_append_odinm9() {
	install -m 0644 ${WORKDIR}/${MACHINE}/splash.bin ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/splash.bin
}
do_install_append_tmtwin() {
	install -m 0644 ${WORKDIR}/${MACHINE}/splash.bmp ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/splash.bmp
}

def vixcorechangeword(file):
	fn = file[:-1]
	os.system('sed -i "s/STB_BOX/' + MACHINE1 + '/g" ' + fn)

do_patch_prepend(){
	global MACHINE1
	if "${MACHINE}" == "vuuno":
		MACHINE1="Vu+ Uno"
	elif "${MACHINE}" == "vuultimo":
		MACHINE1="Vu+ Ultimo"
	elif "${MACHINE}" == "vusolo":
		MACHINE1="Vu+ Solo"
	elif "${MACHINE}" == "vuduo":
		MACHINE1="Vu+ Duo"
	elif "${MACHINE}" == "et5x00":
		MACHINE1="Xtrend ET5"
	elif "${MACHINE}" == "et6x00":
		MACHINE1="Xtrend ET6"
	elif "${MACHINE}" == "et9x00":
		MACHINE1="Xtrend ET9"
	elif "${MACHINE}" == "gb800solo":
		MACHINE1="GigaBlue HD 800 Solo"
	elif "${MACHINE}" == "gb800se":
		MACHINE1="GigaBlue HD 800 SE"
	elif "${MACHINE}" == "gb800ue":
		MACHINE1="GigaBlue HD 800 UE"
	elif "${MACHINE}" == "gbquad":
		MACHINE1="GigaBlue HD Quad"
	elif "${MACHINE}" == "odinm9":
		MACHINE1="Odin M9"
	elif "${MACHINE}" == "tmtwin":
		MACHINE1="Technomate"
	import os
	os.system("find ./ -name \"*.po\" > ./po_list")
	os.system("find ./ -name \"*.py\" >> ./po_list")
	os.system("find ./ -name \"*.xml\" >> ./po_list")
	po_list = []
	po_list = open('po_list','r+').readlines()
	for x in po_list:
		vixcorechangeword(x)
	os.system('rm po_list')
}

def vixcorechangeword2(file):
	fn = file[:-1]
	os.system('sed -i "s/' + MACHINE1 + '/STB_BOX/g" ' + fn)

python do_setup_po_ipk () {
	global MACHINE1
	if "${MACHINE}" == "vuuno":
		MACHINE1="Vu+ Uno"
	elif "${MACHINE}" == "vuultimo":
		MACHINE1="Vu+ Ultimo"
	elif "${MACHINE}" == "vusolo":
		MACHINE1="Vu+ Solo"
	elif "${MACHINE}" == "vuduo":
		MACHINE1="Vu+ Duo"
	elif "${MACHINE}" == "et5x00":
		MACHINE1="Xtrend ET5"
	elif "${MACHINE}" == "et6x00":
		MACHINE1="Xtrend ET6"
	elif "${MACHINE}" == "et9x00":
		MACHINE1="Xtrend ET9"
	elif "${MACHINE}" == "gb800solo":
		MACHINE1="GigaBlue HD 800 Solo"
	elif "${MACHINE}" == "gb800se":
		MACHINE1="GigaBlue HD 800 SE"
	elif "${MACHINE}" == "gb800ue":
		MACHINE1="GigaBlue HD 800 UE"
	elif "${MACHINE}" == "gbquad":
		MACHINE1="GigaBlue HD Quad"
	elif "${MACHINE}" == "odinm9":
		MACHINE1="Odin M9"
	elif "${MACHINE}" == "tmtwin":
		MACHINE1="Technomate"
	import os
	os.system("find ./ -name \"*.po\" > ./po_list")
	os.system("find ./ -name \"*.py\" >> ./po_list")
	os.system("find ./ -name \"*.xml\" >> ./po_list")
	po_list = []
	po_list = open('po_list','r+').readlines()
	for x in po_list:
		vixcorechangeword2(x)
	os.system('rm po_list')
}

do_install_append() {
	find ${D}/usr/lib/enigma2/python/ -name '*.pyc' -exec rm {} \;
}

do_install_po() {
	install -d ${D}${datadir}/enigma2/po/
	LANGS="ar bg ca cs da de el en en_GB es et fa fi fr fy he hr hu is it lt lv nl no pl pt ru sv sk sl sr th tr uk"
	for lang in ${LANGS}; do
		install -m 0755 ${S}/po/$lang.po ${D}${datadir}/enigma2/po/vix-core-$lang.po
	done
}

addtask setup_po_ipk before do_package after do_install
addtask install_po before do_package after do_setup_po_ipk
