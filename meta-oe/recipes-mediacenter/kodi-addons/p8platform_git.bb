SUMMARY = "Platform support library used by libCEC and binary add-ons for Kodi"
HOMEPAGE = "http://libcec.pulse-eight.com/"

PACKAGE_ARCH = "${MACHINE}"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://src/os.h;md5=91686c8014183e8b66aa3a582d9bfd9b"

PV = "2.2.0"

SRC_URI = "git://github.com/Pulse-Eight/platform.git;branch=master;protocol=https"
SRCREV = "2748be52ae27e6007ef548b697d4a03ff7de4291"

inherit cmake pkgconfig

EXTRA_OECMAKE += "-DCMAKE_INSTALL_LIBDIR=${libdir} -DCMAKE_INSTALL_LIBDIR_NOARCH=${libdir}"

FILES:${PN}-dev += "${libdir}/p8-platform"

do_rm_work() {
}