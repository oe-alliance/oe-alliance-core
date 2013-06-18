FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC = "61"

DEPENDS = " \
	ethtool \
	freetype \
	gettext-native \
	gst-plugins-base gstreamer \
	hotplug-e2-helper \
	jpeg \
	libdreamdvd libdvbsi++ libfribidi libmad libpng libsigc++-1.2 libungif libxml2 libxmlccwrap \
	openssl \
	python python-imaging python-twisted python-wifi \
	swig-native \
	tuxtxt-enigma2 \
	"

RDEPENDS_${PN} = " \
	alsa-conf \
	enigma2-fonts \
	ethtool \
	glibc-gconv-iso8859-15 \
	hotplug-e2-helper \
	${PYTHON_RDEPS} \
	"

RRECOMMENDS_${PN} = " \
	glib-networking \
	gst-plugin-subsink \
	gst-plugin-libxt \
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
	python-imaging \
	"

# DVD playback is integrated, we need the libraries
RDEPENDS_${PN} += "libdreamdvd"
RRECOMMENDS_${PN} += "libdvdcss"

# We depend on the font which we use for TXT subtitles (defined in skin_subtitles.xml)
RDEPENDS_${PN} += "font-valis-enigma"

RDEPENDS_${PN} += "${@base_contains("MACHINE_FEATURES", "blindscan-dvbc", "virtual/blindscan-dvbc" , "", d)}"

#make sure default skin is installed.
RDEPENDS_${PN} += "${E2DEFAULTSKIN} "

PV = "2.8+git${SRCPV}"
PKGV = "2.8+git${GITPKGV}"
SRC_URI = "${ENIGMA2_URI}"


SRC_URI_append_azboxhd = " \
			file://azboxe2.patch \
 			file://lcdchar.patch \
 			file://vfdskin.patch \
			"
SRC_URI_append_azboxme = " \
 			file://azboxe2.patch \
 			file://vfdskin.patch \
			"

SRC_URI_append_azboxminime = " \
 			file://azboxe2.patch \
			"

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
FILES_${PN}-po = "${datadir}/enigma2/po/*.po ${datadir}/enigma2/po/*.pot"

do_configure_prepend() {
	if [ "${MACHINE}" = "vusolo" -o "${MACHINE}" = "vuduo" -o "${MACHINE}" = "vusolo2" -o "${MACHINE}" = "vuduo2" -o "${MACHINE}" = "vuuno" -o "${MACHINE}" = "vuultimo" ]; then
		DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/vuplus/vuplus-dvb-modules-${MACHINE}.bb | cut -b 12-19`
	elif [ "${MACHINE}" = "et4x00" -o "${MACHINE}" = "et5x00" -o "${MACHINE}" = "et6x00" -o "${MACHINE}" = "et9x00" ]; then
		DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/etxx00/et-dvb-modules-${MACHINE}.bb | cut -b 12-19`
	elif [ "${MACHINE}" = "odinm9" -o "${MACHINE}" = "odinm7" -o "${MACHINE}" = "e3hd" ]; then
		DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/odin/odin-dvb-modules-${MACHINE}.bb | cut -b 12-19`
	elif [ "${MACHINE}" = "iqonios100hd" -o "${MACHINE}" = "iqonios200hd" -o "${MACHINE}" = "iqonios300hd" -o "${MACHINE}" = "tmtwin" -o "${MACHINE}" = "tm2t" -o "${MACHINE}" = "tmsingle" -o "${MACHINE}" = "tmnano" ]; then
		DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/iqon/iqon-dvb-modules.bb | cut -b 12-19`
	elif [ "${MACHINE}" = "gb800solo" -o "${MACHINE}" = "gb800se" -o "${MACHINE}" = "gb800ue" -o "${MACHINE}" = "gbquad" ]; then
		DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/gigablue/gigablue-dvb-modules-${MACHINE}.bb | cut -b 12-19`
	elif [ "${MACHINE}" = "ventonhde" ]; then
		DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/ini/ini-dvb-modules-inihde.bb | cut -b 12-19`
	elif [ "${MACHINE}" = "ventonhdx" ]; then
		DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/ini/ini-dvb-modules-inihdx.bb | cut -b 12-19`
	elif [ "${MACHINE}" = "xp1000" ]; then
		DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/xp/xp-dvb-modules-${MACHINE}.bb | cut -b 12-19`
	elif [ "${MACHINE}" = "ebox5000" -o "${MACHINE}" = "ebox5100" -o "${MACHINE}" = "ebox7358" ]; then
		DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/ebox/ebox-dvb-modules-${MACHINE}.bb | cut -b 12-19`
	elif [ "${MACHINE}" = "ixussone" -o "${MACHINE}" = "ixusszero" -o "${MACHINE}" = "ixussduo" ]; then
		DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/ixuss/ixuss-dvb-modules-${MACHINE}.bb | cut -b 12-19`
	elif [ "${MACHINE}" = "dm8000" -o "${MACHINE}" = "dm7020hd" -o "${MACHINE}" = "dm500hd" -o "${MACHINE}" = "dm800se" ]; then
		DRIVERSDATE="20130501"
	elif [ "${MACHINE}" = "dm800" ]; then
		DRIVERSDATE="20130501"	
	else
		DRIVERSDATE='N/A'
	fi
}

EXTRA_OECONF += "\
	--with-po \
	--with-distro=${DISTRO_NAME} \
	--with-machinebrand=${MACHINE_BRAND} \
	--with-machinename=${MACHINE_NAME} \
	--with-imageversion=${DISTRO_VERSION} \
	--with-imagebuild=${BUILD_VERSION} \
	--with-driverdate=${DRIVERSDATE} \
	${@base_contains("MACHINE_FEATURES", "colorlcd128", "--with-colorlcd128" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "colorlcd220", "--with-colorlcd220" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "bwlcd255", "--with-bwlcd255" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "fullgraphiclcd", "--with-fullgraphiclcd" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "gigabluelcd", "--with-gigabluelcd" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "nolcd", "--with-nolcd" , "", d)} \
	"

# some plugins contain so's, their stripped symbols should not end up in the enigma2 package
FILES_${PN}-dbg += "\
	/usr/lib/enigma2/python/*/.debug \
	/usr/lib/enigma2/python/*/*/*.debug \
	/usr/lib/enigma2/python/*/*/*/.debug \
	/usr/lib/enigma2/python/*/*/*/*/.debug \
	"

FILES_${PN}-src += "\
	/usr/lib/enigma2/python/upgrade.py \
	/usr/lib/enigma2/python/PowerTimer.py \
	"

do_openpli_preinstall() {
	if [ -e ${S}/data/${RADIOMVI} ]; then
		ln -f ${S}/data/${RADIOMVI} ${S}/data/radio.mvi
		install -d ${D}${sysconfdir}/enigma2
	fi
}

do_install_append() {
	install -d ${D}/usr/share/keymaps
	find ${D}/usr/lib/enigma2/python/ -name '*.pyc' -exec rm {} \;
	ln -s /usr/lib/enigma2/python/Tools/StbHardware.pyo ${D}/usr/lib/enigma2/python/Tools/DreamboxHardware.pyo
	ln -s /usr/lib/python/Components/PackageInfo.pyo ${D}/usr/lib/enigma2/python/Components/DreamboxInfoHandler.pyo
	install -d ${D}${sysconfdir}
	git --git-dir=${S}/.git log --since=10.weeks --pretty=format:"%s" > ${D}${sysconfdir}/e2-git.log
	git --git-dir=${OE-ALLIANCE_BASE}/meta-oe-alliance/.git log --since=10.weeks --pretty=format:"%s" > ${D}${sysconfdir}/oe-git.log
}

python populate_packages_prepend() {
	enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
}
