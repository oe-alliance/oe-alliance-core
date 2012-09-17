FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC = "18"

RDEPENDS_${PN} = " \
	alsa-conf \
	enigma2-fonts \
	ethtool \
	glibc-gconv-iso8859-15 \
	gst-plugin-subsink \
	hotplug-e2-helper \
	\
	${PYTHON_RDEPS} \
	${GST_BASE_RDEPS} \
	${GST_GOOD_RDEPS} \
	${GST_BAD_RDEPS} \
	${GST_UGLY_RDEPS} \
	"

PYTHON_RDEPS += " \
	python-email \
	python-mime \
	python-pyusb \
	python-subprocess \
	python-process \
	"

# We depend on the font which we use for TXT subtitles (defined in skin_subtitles.xml)
DEPENDS += "font-valis-enigma"
RDEPENDS_${PN} += "font-valis-enigma"

RDEPENDS_${PN} += "${@base_contains("MACHINE_FEATURES", "blindscan-dvbc", "virtual/blindscan-dvbc" , "", d)}"

#make sure default skin is installed.
RDEPENDS_${PN} += "${E2DEFAULTSKIN} "

PV = "2.8+git${SRCPV}"
PKGV = "2.8+git${GITPKGV}"
SRC_URI = "${ENIGMA2_URI}"

SRC_URI_append_vuduo = " \
			file://duo_VFD.patch \
			"
SRC_URI_append_gb800solo = " \
			file://gb800-evfd.patch \
			"
SRC_URI_append_gb800se = " \
			file://gb800-evfd.patch \
			"
SRC_URI_append_gb800ue = " \
			file://gb800-evfd.patch \
			"

FILES_${PN} += " ${bindir} ${sysconfdir}/e2-git.log"

# Save po files
PACKAGES =+ "${PN}-po"
FILES_${PN}-po = "${datadir}/enigma2/po/*.po"

EXTRA_OECONF += "\
	--with-po \
	${@base_contains("MACHINE_FEATURES", "fullgraphiclcd", "--with-fullgraphiclcd" , "", d)} \
	"

# some plugins contain so's, their stripped symbols should not end up in the enigma2 package
FILES_${PN}-dbg += "\
	/usr/lib/enigma2/python/*/.debug \
	/usr/lib/enigma2/python/*/*/*.debug \
	/usr/lib/enigma2/python/*/*/*/.debug \
	/usr/lib/enigma2/python/*/*/*/*/.debug \
	"

def enigma2changeword(file):
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
	elif "${MACHINE}" == "vuduo":
		MACHINE1="Vu+ Duo"
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
		MACHINE1="Technomate"
	elif "${MACHINE}" == "ventonhdx":
		MACHINE1="Venton HD"
	import os
	os.system("find ./ -name \"*.po\" > ./po_list")
	os.system("find ./ -name \"*.py\" >> ./po_list")
	os.system("find ./ -name \"*.xml\" >> ./po_list")
	po_list = []
	po_list = open('po_list','r+').readlines()
	for x in po_list:
		enigma2changeword(x)
	os.system('rm po_list')
}

def enigma2changeword2(file):
	fn = file[:-1]
	os.system('sed -i "s/' + MACHINE1 + '/STB_BOX/g" ' + fn)

python do_setup_po_ipk () {
	global MACHINE1
	MACHINE1="Dreambox"
	if "${MACHINE}" == "vuuno":
		MACHINE1="Vu+ Uno"
	elif "${MACHINE}" == "vuultimo":
		MACHINE1="Vu+ Ultimo"
	elif "${MACHINE}" == "vusolo":
		MACHINE1="Vu+ Solo"
	elif "${MACHINE}" == "vuduo":
		MACHINE1="Vu+ Duo"
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
		MACHINE1="Technomate"
	elif "${MACHINE}" == "ventonhdx":
		MACHINE1="Venton HD"
	import os
	os.system("find ./ -name \"*.po\" > ./po_list")
	os.system("find ./ -name \"*.py\" >> ./po_list")
	os.system("find ./ -name \"*.xml\" >> ./po_list")
	po_list = []
	po_list = open('po_list','r+').readlines()
	for x in po_list:
		enigma2changeword2(x)
	os.system('rm po_list')
}

do_openpli_preinstall() {
	if [ -e ${S}/data/${RADIOMVI} ]; then
		ln -f ${S}/data/${RADIOMVI} ${S}/data/radio.mvi
		install -d ${D}${sysconfdir}/enigma2
	fi
}

do_install_append() {
	install -d ${D}/usr/share/keymaps
	find ${D}/usr/lib/enigma2/python/ -name '*.pyc' -exec rm {} \;
	find ${D}/usr/lib/enigma2/python/Plugins/ -name '*.py' -exec rm {} \;
	if [ -e ${S}/lib/python/Plugins/Extensions/DVDPlayer/plugin.py ]; then
		cp ${S}/lib/python/Plugins/Extensions/DVDPlayer/plugin.py ${D}/usr/lib/enigma2/python/Plugins/Extensions/DVDPlayer/plugin.py
	fi
	install -d ${D}${sysconfdir}
	git --git-dir=${S}/.git log --since=10.weeks --pretty=format:"%s" > ${D}${sysconfdir}/e2-git.log
}

do_install_po() {
	LANGS="ar bg ca cs da de el en en_GB es et fa fi fr fy he hr hu is it lt lv nl no pl pt ro ru sv sk sl sr th tr uk"
	for lang in ${LANGS}; do
		if [ -e ${S}/po/$lang.po ]; then
			install -m 0755 ${S}/po/$lang.po ${D}${datadir}/enigma2/po/enigma2-$lang.po
		fi
	done
}

addtask setup_po_ipk before do_package after do_install
addtask install_po before do_package after do_setup_po_ipk

python populate_packages_prepend() {
	enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
}
