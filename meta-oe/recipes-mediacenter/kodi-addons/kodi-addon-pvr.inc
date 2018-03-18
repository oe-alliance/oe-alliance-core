LICENSE = "GPLv2+"
require conf/license/license-gplv2.inc

DEPENDS += " \
            zip-native \
            p8platform \
            kodi-platform \
            libtinyxml2 \
            jsoncpp \
            jsonrpc \
            virtual/kodi \
          "

PACKAGE_ARCH = "${MACHINE}"

SRCREV_FORMAT = "pvr${KODIADDONPLUGIN}"

PV = "2.4.9+git${SRCPV}"
PKGV = "2.4.9+git${GITPKGV}"
KODIADDONBRANCH ?= "Krypton"

SRC_URI = "git://github.com/kodi-pvr/pvr.${KODIADDONPLUGIN}.git;destsuffix=pvr.${KODIADDONPLUGIN};name=pvr${KODIADDONPLUGIN};branch=${KODIADDONBRANCH}"

inherit cmake pkgconfig gettext gitpkgv

S = "${WORKDIR}/pvr.${KODIADDONPLUGIN}"

EXTRA_OECMAKE = " \
    -DADDONS_TO_BUILD=pvr.${KODIADDONPLUGIN} \
    -DADDON_SRC_PREFIX=${WORKDIR}/git \
    -DCMAKE_BUILD_TYPE=Debug -DVMTRACE=1 -DPARANOIA=1 \
    -DCMAKE_INSTALL_PREFIX=${datadir}/kodi/addons \
    -DKODI_INCLUDE_DIR=${STAGING_INCDIR}/kodi \
    -DCMAKE_MODULE_PATH='${STAGING_DIR_HOST}${libdir}/kodi;${STAGING_DIR_HOST}${datadir}/kodi/cmake' \
    -DCMAKE_PREFIX_PATH=${STAGING_DIR_HOST}${prefix} \
    -DJSONCPP_INCLUDE_DIRS=${STAGING_INCDIR}/json \
    -DPACKAGE_ZIP=1 \
"

do_compile_prepend() {
	sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' \
	       -e 's:-pipe:${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} -pipe:g' \
		   -e 's:/recipe-sysroot-native/usr/include:/recipe-sysroot/usr/include:g' \
	          ${B}/CMakeFiles/*/flags.make
	sed -i -e 's:-pipe:${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} -pipe:g'\
	          ${B}/CMakeFiles/*/link.txt
}


# Make zip package for manual installation
do_install_append() {
	install -d ${D}${datadir}/kodi/addons/packages/
	( cd ${D}${datadir}/kodi/addons
	  zip -r ${D}${datadir}/kodi/addons/packages/pvr.${KODIADDONPLUGIN}-${PV}.zip pvr.${KODIADDONPLUGIN} -x '*.debug*' )
}

# Doesn't get added automagically, dlopen()?
RDEPENDS_${PN} += "libkodiplatform"

INSANE_SKIP_${PN} = "dev-so"
FILES_${PN} += "${datadir}/kodi"
FILES_${PN}-dbg += "${datadir}/kodi/addons/*/.debug/"

do_qa_staging() {
}
