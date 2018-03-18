SUMMARY = "Platform support library used by libCEC and binary add-ons for Kodi"
HOMEPAGE = "http://libcec.pulse-eight.com/"

PACKAGE_ARCH = "${MACHINE}"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://src/os.h;md5=752555fa94e82005d45fd201fee5bd33"

PV = "2.2.0"

SRC_URI = "git://github.com/Pulse-Eight/platform.git"
SRCREV = "a822e196cb57d8545dccca6cc22fda0f83c34321"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

EXTRA_OECMAKE += "-DCMAKE_INSTALL_LIBDIR=${libdir} -DCMAKE_INSTALL_LIBDIR_NOARCH=${libdir}"

FILES_${PN}-dev += "${libdir}/p8-platform"

do_rm_work() {
}