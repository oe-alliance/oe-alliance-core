LICENSE = "GPLv2+"
require conf/license/license-gplv2.inc
require ${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "kodi-leia.inc", "kodi-krypton.inc", d)}

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


PVRMYTV = "${@bb.utils.contains("KODIADDONPLUGIN", "mythtv", "janbar", "kodi-pvr", d)}"

PVRGIT = "${@bb.utils.contains("MACHINE_FEATURES", "kodi18", "${PVRMYTV}", "kodi-pvr", d)}"

SRC_URI = "git://github.com/${PVRGIT}/pvr.${KODIADDONPLUGIN}.git;destsuffix=pvr.${KODIADDONPLUGIN};name=pvr${KODIADDONPLUGIN};branch=${KODIADDONBRANCH}"

inherit cmake pkgconfig gettext gitpkgv

S = "${WORKDIR}/pvr.${KODIADDONPLUGIN}"

OECMAKE_GENERATOR = "Unix Makefiles"

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
    -DXLOCALE_NOT_USED=1 \
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

INSANE_SKIP_${PN} = "dev-so file-rdeps"
FILES_${PN} += "${datadir}/kodi"
FILES_${PN}-dbg += "${datadir}/kodi/addons/*/.debug/"

do_qa_staging() {
}
