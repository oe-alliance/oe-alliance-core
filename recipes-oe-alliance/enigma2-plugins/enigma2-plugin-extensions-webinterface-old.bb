DESCRIPTION = "Old webinterface (avoid license issues)"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

DEPENDS = "enigma2 python-pyopenssl python-twisted"
RDEPENDS_${PN} = "python-twisted-web python-pyopenssl python-crypt python-unixadmin aio-grab"
PACKAGES = "${PN}-src ${PN}"
RPROVIDES_${PN} = "enigma2-plugin-extensions-webinterface"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r0"

# REMOTE GIT
SRC_URI="git://git.code.sf.net/p/openpli/plugins-enigma2;protocol=git;branch=old \
	 file://buildonlywebif.patch \
	 file://webiftpm.patch \
	"
SRC_URI_append_vuuno = " \
			file://enigma2_plugins_WebInterface.patch \
			file://dreamboxweb.png \
			file://favicon.ico \
			file://rc.png \
			file://dreamboxwebtv.png"
SRC_URI_append_vuultimo = " \
			file://enigma2_plugins_WebInterface.patch \
			file://dreamboxweb.png \
			file://favicon.ico \
			file://rc.png \
			file://dreamboxwebtv.png"
SRC_URI_append_vusolo = " \
			file://enigma2_plugins_WebInterface.patch \
			file://dreamboxweb.png \
			file://favicon.ico \
			file://rc.png \
			file://dreamboxwebtv.png"
SRC_URI_append_vuduo = " \
			file://enigma2_plugins_WebInterface.patch \
			file://dreamboxweb.png \
			file://favicon.ico \
			file://rc.png \
			file://dreamboxwebtv.png"

SRC_URI_append_et4x00 = " \
			file://enigma2_plugins_WebInterface.patch \
			file://dreamboxweb.png \
			file://favicon.ico \
			file://rc.png \
			file://dreamboxwebtv.png"
SRC_URI_append_et5x00 = " \
			file://enigma2_plugins_WebInterface.patch \
			file://dreamboxweb.png \
			file://favicon.ico \
			file://rc.png \
			file://dreamboxwebtv.png"
SRC_URI_append_et6x00 = " \
			file://enigma2_plugins_WebInterface.patch \
			file://dreamboxweb.png \
			file://favicon.ico \
			file://rc.png \
			file://dreamboxwebtv.png"
SRC_URI_append_et9x00 = " \
			file://enigma2_plugins_WebInterface.patch \
			file://dreamboxweb.png \
			file://favicon.ico \
			file://rc.png \
			file://dreamboxwebtv.png"
SRC_URI_append_odinm9= " \
			file://enigma2_plugins_WebInterface.patch \
			file://dreamboxweb.png \
			file://favicon.ico \
			file://rc.png \
			file://dreamboxwebtv.png"

S = "${WORKDIR}/git"


EXTRA_OECONF = " \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	--without-debug \
"

inherit autotools

S = "${WORKDIR}/git"

PLUGINPATH = "/usr/lib/enigma2/python/Plugins/Extensions/WebInterface"

FILES_${PN}-src = "${PLUGINPATH}/*.py ${PLUGINPATH}/*/*.py ${PLUGINPATH}/*/*/*.py"
FILES_${PN} = "/usr/lib/enigma2/python/Plugins/Extensions/WebInterface"

def modify_webif_po():
	import os
	try:
		os.system("find ./ -name \"*.po\" > ./po_list")
		os.system("find ./ -name \"*.pot\" >> ./po_list")
		po_list = []
		po_list = open('po_list','r+').readlines()
		for x in po_list:
			changewebif(x)
		changewebif('git/webinterface/src/web-data/tpl/default/index.html ')
		os.system('rm po_list')
	except:
		print 'word patch error '
		return

def changewebif(file):
	fn = file[:-1]
	fnn = file[:-1]+'_n'
	cmd = "sed s/Dreambox/Receiver/g "+fn+" > "+fnn
	os.system(cmd)
	cmd1 = "mv "+fnn+" "+fn
	os.system(cmd1)

do_unpack_append(){
	modify_webif_po()
}

do_install_append_vuuno() {
	install -m 0644 ${WORKDIR}/dreamboxweb.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/dreamboxwebtv.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/tpl/default/streaminterface/img
	install -m 0644 ${WORKDIR}/favicon.ico ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/rc.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
}
do_install_append_vuultimo() {
	install -m 0644 ${WORKDIR}/dreamboxweb.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/dreamboxwebtv.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/tpl/default/streaminterface/img
	install -m 0644 ${WORKDIR}/favicon.ico ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/rc.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
}
do_install_append_vusolo() {
	install -m 0644 ${WORKDIR}/dreamboxweb.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/dreamboxwebtv.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/tpl/default/streaminterface/img
	install -m 0644 ${WORKDIR}/favicon.ico ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/rc.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
}
do_install_append_vuduo() {
	install -m 0644 ${WORKDIR}/dreamboxweb.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/dreamboxwebtv.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/tpl/default/streaminterface/img
	install -m 0644 ${WORKDIR}/favicon.ico ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/rc.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
}

do_install_append_et4x00() {
	install -m 0644 ${WORKDIR}/dreamboxweb.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/dreamboxwebtv.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/tpl/default/streaminterface/img
	install -m 0644 ${WORKDIR}/favicon.ico ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/rc.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
}
do_install_append_et5x00() {
	install -m 0644 ${WORKDIR}/dreamboxweb.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/dreamboxwebtv.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/tpl/default/streaminterface/img
	install -m 0644 ${WORKDIR}/favicon.ico ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/rc.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
}
do_install_append_et6x00() {
	install -m 0644 ${WORKDIR}/dreamboxweb.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/dreamboxwebtv.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/tpl/default/streaminterface/img
	install -m 0644 ${WORKDIR}/favicon.ico ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/rc.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
}
do_install_append_et9x00() {
	install -m 0644 ${WORKDIR}/dreamboxweb.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/dreamboxwebtv.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/tpl/default/streaminterface/img
	install -m 0644 ${WORKDIR}/favicon.ico ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/rc.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
}

do_install_append_odinm9() {
	install -m 0644 ${WORKDIR}/dreamboxweb.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/dreamboxwebtv.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/tpl/default/streaminterface/img
	install -m 0644 ${WORKDIR}/favicon.ico ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
	install -m 0644 ${WORKDIR}/rc.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/web-data/img/
}

do_install_append() {
	rm -rf ${D}/usr/share
	# remove unused .pyc files
	find ${D}/usr/lib/enigma2/python/ -name '*.pyc' -exec rm {} \;
}

pkg_postinst_${PN}_append() {
	if [ -f /usr/lib/enigma2/python/Plugins/Extensions/WebInterface/WebChilds/External/__init__.py ]
	then
		rm /usr/lib/enigma2/python/Plugins/Extensions/WebInterface/WebChilds/External/__init__.py
	fi
	if [ -f /usr/lib/enigma2/python/Plugins/Extensions/WebInterface/WebChilds/__init__.py ]
	then
		rm /usr/lib/enigma2/python/Plugins/Extensions/WebInterface/WebChilds/__init__.py
	fi
	if [ -f /usr/lib/enigma2/python/Plugins/Extensions/WebInterface/WebChilds/Toplevel.py ]
	then
		rm /usr/lib/enigma2/python/Plugins/Extensions/WebInterface/WebChilds/Toplevel.py
	fi
	if [ -f /usr/lib/enigma2/python/Plugins/Extensions/WebInterface/__init__.py ]
	then
		rm /usr/lib/enigma2/python/Plugins/Extensions/WebInterface/__init__.py
	fi
}

