SUMMARY = "Platform support library used by libCEC and binary add-ons for Kodi"
HOMEPAGE = "http://libcec.pulse-eight.com/"

PACKAGE_ARCH = "${MACHINE}"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://src/util/XMLUtils.cpp;beginline=2;endline=18;md5=dae8e846500e70dd8ecee55f3f018c30"

DEPENDS = "libtinyxml virtual/kodi"

PV = "17.2.0"

SRCREV = "e8574b883ffa2131f2eeb96ff3724d60b21130f7"
SRC_URI = "git://github.com/xbmc/kodi-platform.git \
           file://0001-fix-cross-compile-badness.patch \
           file://kodi-platform-02_no-multi-lib.patch \
          "

S = "${WORKDIR}/git"

inherit cmake pkgconfig

EXTRA_OECMAKE = " -DCMAKE_INSTALL_PREFIX_TOOLCHAIN=${STAGING_DIR_HOST}${prefix} \
                   -DCMAKE_INSTALL_LIBDIR=${libdir} \
                   -DCMAKE_INSTALL_LIBDIR_NOARCH=${libdir} \
                   -DKODI_INCLUDE_DIR=${STAGING_INCDIR}/kodi \
                   -DCMAKE_MODULE_PATH='${STAGING_DIR_HOST}${libdir}/kodi;${STAGING_DIR_HOST}${datadir}/kodi/cmake' \
                   -DCMAKE_PREFIX_PATH=${STAGING_DIR_HOST}${prefix} \
                 "

do_compile_prepend() {
	sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' \
	       -e 's:-pipe:${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} -pipe:g' \
	          ${B}/CMakeFiles/kodiplatform.dir/flags.make
	sed -i -e 's:-pipe:${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} -pipe:g'\
	          ${B}/CMakeFiles/kodiplatform.dir/link.txt
}

do_install_append() {
	sed -i -e '/CXX/d' \
               -e '/CC/d' \
               -e 's:${STAGING_LIBDIR}:${libdir}:g' \
               -e 's:${STAGING_DIR_HOST}:/:g' \
               -e 's:${STAGING_DIR_NATIVE}:/:g' \
               -e 's:${prefix}/${libdir}:${libdir}:g' \
               -e 's:${WORKDIR}=::g' \ 
            ${D}${libdir}/pkgconfig/*.pc
}

RPROVIDES_${PN} += "libkodiplatform"
PACKAGES =+ "libkodiplatform"

FILES_libkodiplatform = "${libdir}/lib*.so.*"

FILES_${PN}-dev += "${libdir}/*platform"

do_qa_staging() {
}

do_rm_work() {
}