MAINTAINER = "OpenvViX team <info@world-of-satellite.com>"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC = "1"

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r2"

SRC_URI = "${ENIGMA2_PLUGINS_URI}"

SRC_URI_append_vuuno = " \
			file://dreambox_bouqueteditor.png \
			file://FC2dreambox.png \
"
SRC_URI_append_vuultimo = " \
			file://dreambox_bouqueteditor.png \
			file://FC2dreambox.png \
"
SRC_URI_append_vusolo = " \
			file://dreambox_bouqueteditor.png \
			"
SRC_URI_append_vuduo = " \
			file://dreambox_bouqueteditor.png \
			"

SRC_URI_append_et5x00 = " \
			file://dreambox_bouqueteditor.png \
			"
SRC_URI_append_et6x00 = " \
			file://dreambox_bouqueteditor.png \
			"
SRC_URI_append_et9x00 = " \
			file://dreambox_bouqueteditor.png \
			"

SRC_URI_append_odinm9 = " \
			file://dreambox_bouqueteditor.png \
			"

SRC_URI_append_tmtwin = " \
			file://dreambox_bouqueteditor.png \
			"

EXTRA_OECONF += "\
	--with-po --with-boxtype=${MACHINE} \
"

# Save po files
PACKAGES += "${PN}-po"

DEPENDS += " nmap \
			${@base_contains("MACHINE_FEATURES", "tpm", "" , "enigma2-plugin-extensions-webinterface-old", d)}"

do_install_append_vuuno() {
	install -m 0644 ${WORKDIR}/FC2dreambox.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/FanControl2/data/
	install -m 0644 ${WORKDIR}/dreambox_bouqueteditor.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebBouquetEditor/web-data/
}
do_install_append_vuultimo() {
	install -m 0644 ${WORKDIR}/FC2dreambox.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/FanControl2/data/
	install -m 0644 ${WORKDIR}/dreambox_bouqueteditor.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebBouquetEditor/web-data/
}
do_install_append_vusolo() {
	install -m 0644 ${WORKDIR}/dreambox_bouqueteditor.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebBouquetEditor/web-data/
}
do_install_append_vuduo() {
	install -m 0644 ${WORKDIR}/dreambox_bouqueteditor.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebBouquetEditor/web-data/
}
do_install_append_et5x00() {
	install -m 0644 ${WORKDIR}/dreambox_bouqueteditor.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebBouquetEditor/web-data/
}
do_install_append_et6x00() {
	install -m 0644 ${WORKDIR}/dreambox_bouqueteditor.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebBouquetEditor/web-data/
}
do_install_append_et9x00() {
	install -m 0644 ${WORKDIR}/dreambox_bouqueteditor.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebBouquetEditor/web-data/
}
do_install_append_odinm9() {
	install -m 0644 ${WORKDIR}/dreambox_bouqueteditor.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebBouquetEditor/web-data/
}
do_install_append_tmtwin() {
	install -m 0644 ${WORKDIR}/dreambox_bouqueteditor.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebBouquetEditor/web-data/
}

python populate_packages_prepend () {
	enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)

	do_split_packages(d, enigma2_plugindir, '(.*?/.*?)/.*', 'enigma2-plugin-%s', 'Enigma2 Plugin: %s', recursive=True, match_path=True, prepend=True)

	def getControlLines(mydir, d, package):
		import os
		try:
			#ac3lipsync is renamed since 20091121 to audiosync.. but rename in cvs is not possible without lost of revision history..
			#so the foldername is still ac3lipsync
			if package == 'audiosync':
				package = 'ac3lipsync'
			src = open(mydir + package + "/CONTROL/control").read()
		except IOError:
			return
		for line in src.split("\n"):
			if line.startswith('Package: '):
				full_package = line[9:]
			elif line.startswith('Depends: '):
				# some plugins still reference twisted-* dependencies, these packages are now called python-twisted-*
				depends = line[9:].replace(',', '').split(' ')
				rdepends = ''
				for depend in depends:
					if depend.startswith('twisted-'):
						rdepends += ' ' + depend.replace('twisted-', 'python-twisted-')
					else:
						rdepends += ' ' + depend
				bb.data.setVar('RDEPENDS_' + full_package, rdepends, d)
			elif line.startswith('Recommends: '):
				bb.data.setVar('RRECOMMENDS_' + full_package, line[12:], d)
			elif line.startswith('Description: '):
				bb.data.setVar('DESCRIPTION_' + full_package, line[13:], d)
			elif line.startswith('Replaces: '):
				bb.data.setVar('RREPLACES_' + full_package, ' '.join(line[10:].split(', ')), d)
			elif line.startswith('Conflicts: '):
				bb.data.setVar('RCONFLICTS_' + full_package, ' '.join(line[11:].split(', ')), d)
			elif line.startswith('Maintainer: '):
				bb.data.setVar('MAINTAINER_' + full_package, line[12:], d)


	mydir = bb.data.getVar('D', d, 1) + "/../git/"
	for package in bb.data.getVar('PACKAGES', d, 1).split():
		getControlLines(mydir, d, package.split('-')[-1])
}

python populate_packages_prepend() {
	enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}
