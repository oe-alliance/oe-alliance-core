SUMMARY = "Platform support library used by libCEC and binary add-ons for Kodi"
HOMEPAGE = "http://libcec.pulse-eight.com/"

PACKAGE_ARCH = "${MACHINE}"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://src/util/XMLUtils.cpp;beginline=2;endline=18;md5=dae8e846500e70dd8ecee55f3f018c30"

DEPENDS = "libtinyxml virtual/kodi"

PV = "21.0.0"

SRCREV = "809c5e9d711e378561440a896fcb7dbcd009eb3d"
SRC_URI = "git://github.com/xbmc/kodi-platform.git;protocol=https;branch=master \
           file://kodi-platform-01_crosscompile-badness.patch \
           file://kodi-platform-02_no-multi-lib.patch \
           file://kodi-platform-03_pkgconfig.patch \
          "

S = "${WORKDIR}/git"

inherit cmake pkgconfig

OECMAKE_GENERATOR="Unix Makefiles"
EXTRA_OECMAKE = " -DCMAKE_INSTALL_PREFIX_TOOLCHAIN=${STAGING_DIR_HOST}${prefix} \
                  -DCMAKE_INSTALL_LIBDIR=${libdir} \
                  -DCMAKE_INSTALL_LIBDIR_NOARCH=${libdir} \
                  -DKODI_INCLUDE_DIR=${STAGING_INCDIR}/kodi \
                  -DCMAKE_MODULE_PATH='${STAGING_DIR_HOST}${libdir}/kodi;${STAGING_DIR_HOST}${datadir}/kodi/cmake' \
                  -DCMAKE_PREFIX_PATH=${STAGING_DIR_HOST}${prefix} \
                "

do_compile:prepend() {
	sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' \
	       -e 's:-pipe:${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} -pipe:g' \
	          ${B}/CMakeFiles/kodiplatform.dir/flags.make
	sed -i -e 's:-pipe:${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} -pipe:g'\
	          ${B}/CMakeFiles/kodiplatform.dir/link.txt
}

do_install:append() {
	sed -i -e '/CXX/d' \
               -e '/CC/d' \
               -e 's:${STAGING_LIBDIR}:${libdir}:g' \
               -e 's:${STAGING_DIR_HOST}:/:g' \
               -e 's:${STAGING_DIR_NATIVE}:/:g' \
               -e 's:${prefix}/${libdir}:${libdir}:g' \
               -e 's:${WORKDIR}=::g' \ 
            ${D}${libdir}/pkgconfig/*.pc
}

RPROVIDES:${PN} += "libkodiplatform"
PACKAGES =+ "libkodiplatform"

FILES:libkodiplatform = "${libdir}/lib*.so.*"

FILES:${PN}-dev += "${libdir}/*platform"

do_qa_staging() {
}

do_rm_work() {
}
