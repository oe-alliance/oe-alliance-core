DESCRIPTION = "Vix Core"
MAINTAINER = "Andy Blackburn"

require conf/license/license-gplv2.inc

PACKAGE_ARCH := "${MACHINE_ARCH}"

DEPENDS = "enigma2 mtd-utils python-process libcrypto-compat"
RDEPENDS = "mtd-utils python-process libcrypto-compat util-linux-blkid"

DEPENDS_append_gb800solo = " gigablue-cfe"
RDEPENDS_append_gb800solo = " gigablue-cfe"

DEPENDS_append_gb800se = " gigablue-cfe"
RDEPENDS_append_gb800se = " gigablue-cfe"

RCONFLICTS_${PN} = "settings-autorestore"
RREPLACES_${PN} = "settings-autorestore"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.1+git${SRCPV}"
PKGV = "2.1+git${GITPKGV}"
PR = "r10"

SRC_URI="git://github.com/OpenViX/vix-core.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools

EXTRA_OECONF = "\
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	--with-po \
	--with-libsdl=no \
	--with-boxtype=${MACHINE} \
"

PACKAGES =+ "${PN}-src"
PACKAGES =+ "${PN}-po"
FILES_${PN} = "/etc /usr/lib"
FILES_${PN}-dbg = "/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/.debug/"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/*.py"
FILES_${PN}-po = "/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/locale/*.po"

# remove unused .pyc files
do_install_append() {
	find ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/ -name '*.pyc' -exec rm {} \;
}

def vixcorechangeword(file):
	fn = file[:-1]
	os.system('sed -i "s/STB_BOX/' + MACHINE1 + '/g" ' + fn)
	os.system('sed -i "s/STB-BOX/' + MACHINE1 + '/g" ' + fn)
	os.system('sed -i "s/STB-GUI/Receiver/g" ' + fn)

do_patch_prepend(){
	global MACHINE1
	MACHINE1="Dreambox"
	if "${MACHINE}" == "vuuno":
		MACHINE1="Vu+ Uno"
	elif "${MACHINE}" == "vuultimo":
		MACHINE1="Vu+ Ultimo"
	elif "${MACHINE}" == "vusolo":
		MACHINE1="Vu+ Solo"
	elif "${MACHINE}" == "vusolo2":
		MACHINE1="Vu+ Solo2"
	elif "${MACHINE}" == "vuduo":
		MACHINE1="Vu+ Duo"
	elif "${MACHINE}" == "vuduo2":
		MACHINE1="Vu+ Duo2"
	elif "${MACHINE}" == "et4x00":
		MACHINE1="Xtrend ET4x00"
	elif "${MACHINE}" == "et5x00":
		MACHINE1="Xtrend ET5x00"
	elif "${MACHINE}" == "et6x00":
		MACHINE1="Xtrend ET6x00"
	elif "${MACHINE}" == "et9x00":
		MACHINE1="Xtrend ET9x00"
	elif "${MACHINE}" == "odinm9":
		MACHINE1="Odin M9"
	elif "${MACHINE}" == "gb800solo":
		MACHINE1="GigaBlue HD 800 Solo"
	elif "${MACHINE}" == "gb800se":
		MACHINE1="GigaBlue HD 800 SE"
	elif "${MACHINE}" == "gb800ue":
		MACHINE1="GigaBlue HD 800 UE"
	elif "${MACHINE}" == "gbquad":
		MACHINE1="GigaBlue HD Quad"
	elif "${MACHINE}" == "tmtwin":
		MACHINE1="Technomate Twin OE"
	elif "${MACHINE}" == "tm2t":
		MACHINE1="Technomate 2T OE"
	elif "${MACHINE}" == "tmsingle":
		MACHINE1="Technomate Single"
	elif "${MACHINE}" == "iqonios100hd":
		MACHINE1="iqon IOS 100HD"
	elif "${MACHINE}" == "iqonios200hd":
		MACHINE1="iqon IOS 200HD"
	elif "${MACHINE}" == "iqonios300hd":
		MACHINE1="iqon IOS 300HD"
	elif "${MACHINE}" == "ventonhdx":
		if "${DISTRO}" == "ventonsupport" or "${DISTRO}" == "egami":
			MACHINE1="linux receiver"
		else:
			MACHINE1="Venton HDx"
	elif "${MACHINE}" == "ventonhde":
		if "${DISTRO}" == "ventonsupport" or "${DISTRO}" == "egami":
			MACHINE1="linux receiver"
		else:
			MACHINE1="Venton HDe"
	elif "${MACHINE}" == "xp1000":
		MACHINE1="MaxDigital XP1000"
	elif "${MACHINE}" == "ebox5000":
		MACHINE1="MixOS 5000"
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
	os.system('sed -i "s/' + MACHINE1 + '/STB-BOX/g" ' + fn)
	os.system('sed -i "s/Receiver/STB-GUI/g" ' + fn)

python do_setup_po_ipk () {
	global MACHINE1
	MACHINE1="Dreambox"
	if "${MACHINE}" == "vuuno":
		MACHINE1="Vu+ Uno"
	elif "${MACHINE}" == "vuultimo":
		MACHINE1="Vu+ Ultimo"
	elif "${MACHINE}" == "vusolo":
		MACHINE1="Vu+ Solo"
	elif "${MACHINE}" == "vusolo2":
		MACHINE1="Vu+ Solo2"
	elif "${MACHINE}" == "vuduo":
		MACHINE1="Vu+ Duo"
	elif "${MACHINE}" == "vuduo2":
		MACHINE1="Vu+ Duo2"
	elif "${MACHINE}" == "et4x00":
		MACHINE1="Xtrend ET4x00"
	elif "${MACHINE}" == "et5x00":
		MACHINE1="Xtrend ET5x00"
	elif "${MACHINE}" == "et6x00":
		MACHINE1="Xtrend ET6x00"
	elif "${MACHINE}" == "et9x00":
		MACHINE1="Xtrend ET9x00"
	elif "${MACHINE}" == "odinm9":
		MACHINE1="Odin M9"
	elif "${MACHINE}" == "gb800solo":
		MACHINE1="GigaBlue HD 800 Solo"
	elif "${MACHINE}" == "gb800se":
		MACHINE1="GigaBlue HD 800 SE"
	elif "${MACHINE}" == "gb800ue":
		MACHINE1="GigaBlue HD 800 UE"
	elif "${MACHINE}" == "gbquad":
		MACHINE1="GigaBlue HD Quad"
	elif "${MACHINE}" == "tmtwin":
		MACHINE1="Technomate Twin OE"
	elif "${MACHINE}" == "tm2t":
		MACHINE1="Technomate 2T OE"
	elif "${MACHINE}" == "tmsingle":
		MACHINE1="Technomate Single"
	elif "${MACHINE}" == "iqonios100hd":
		MACHINE1="iqon IOS 100HD"
	elif "${MACHINE}" == "iqonios200hd":
		MACHINE1="iqon IOS 200HD"
	elif "${MACHINE}" == "iqonios300hd":
		MACHINE1="iqon IOS 300HD"
	elif "${MACHINE}" == "ventonhdx":
		MACHINE1="Venton HD"
	elif "${MACHINE}" == "ventonhde":
		MACHINE1="Venton HDe"
	elif "${MACHINE}" == "xp1000":
		MACHINE1="MaxDigital XP1000"
	elif "${MACHINE}" == "ebox5000":
		MACHINE1="MixOS 5000"
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

do_install_append_gb800solo() {
	install -m 0644 ${DEPLOY_DIR_IMAGE}/burn.bat ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/burn.bat
}

do_install_append_gb800se() {
	install -m 0644 ${DEPLOY_DIR_IMAGE}/burn.bat ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/burn.bat
}

do_install_po() {
	install -d ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/locale
	LANGS="ar bg ca cs da de el en en_GB es et fa fi fr fy he hr hu is it lt lv nl no nb pl pt pt_BR ro ru sv sk sl sr th tr uk"
	for lang in ${LANGS}; do
		install -m 0755 ${S}/po/$lang.po ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/locale/$lang.po
	done
	install -m 0755 ${S}/po/vix.pot ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/ViX/locale/vix.pot
}

addtask setup_po_ipk before do_package after do_install
addtask install_po before do_package after do_setup_po_ipk
