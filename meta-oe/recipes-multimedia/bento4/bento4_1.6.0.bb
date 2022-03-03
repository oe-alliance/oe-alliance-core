SUMMARY  = "C++ class library and tools designed to read and write ISO-MP4 files"
SECTION = "misc"
HOMEPAGE = "http://bento4.com"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM="file://Documents/LICENSE.txt;md5=2bdfce88f437a0613f41effed74b7061"

SRCREV = "83c48e6e2a3f8e4be7ad2eddaa0639303184146d"
PV = "1.6.0-639"

inherit cmake

SRC_URI = "git://github.com/axiomatic-systems/Bento4;protocol=https;branch=master \
        file://0001-Add-additional-methods-funtions-and-passing-poolid.patch \
        file://0002-Backport-Smmothstream-changes.patch \
        file://0003-more-SPS-parameters.patch \
        file://0004-AVC-extract-VUI-values-from-SPS.patch \
        file://0005-Implement-SPS-Frame-parser.patch \
        file://0006-Fix-segfault-when-AP4_Sample-s-seek.patch \
        file://0007-Hack-HBO.patch \
        file://0008-Android-32-ftello-fix.patch \
        file://0009-Dazn-sample-duration-workaround.patch \
        file://0010-Add-argument-to-reuse-single-sample-decrypter.patch \
        file://0011-Static-ReadGolomb-SignedGolomb.patch \
        file://0012-Add-GetChannels-method.patch \
        file://0013-Implemented-GetSampleIndexForTimeStamp-GetNearestSyn.patch \
        file://0014-Ap4SampleDescription-added-missing-dynamic-casts.patch \
        file://0015-Avoid-set-next-fragment-position.patch \
        file://0016-Add-support-for-cmake-install.patch"
SRC_URI:append:mipsel = " file://ap4config-set-mipsel-byte-order.patch"

S = "${WORKDIR}/git"

EXTRA_OECMAKE:append  = " -DBUILD_APPS=OFF"
CXXFLAGS:append = " -fPIC"

ALLOW_EMPTY:${PN} = "1"

do_install() {
    install -d ${D}${includedir}
    mkdir -p ${D}${includedir}/bento4
    find ${S}/Source/C++/ -name '*.h' -exec cp -f "{}" ${D}${includedir}/bento4 \;

    install -d ${D}${libdir}
    cp -f ${B}/libap4.a ${D}${libdir}
}
