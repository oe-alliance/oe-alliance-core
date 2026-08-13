SUMMARY = "Amlogic vendor codec userspace library"
DESCRIPTION = "AArch64 libamcodec ABI used by the Amlogic 4.9 hardware decoder stack"
require conf/license/license-close.inc

LIBAMCODEC_REV = "d28bf07facc8727de52ba1f70c1362f9e19adc25"

SRC_URI = "https://sources.coreelec.org/libamcodec-aarch64-${LIBAMCODEC_REV}.tar.xz"
SRC_URI[sha256sum] = "53134b39125ac5c3d4338cde57c6ec444154628fe17dc1a808a98ef171b2914e"

S = "${UNPACKDIR}/libamcodec-aarch64-${LIBAMCODEC_REV}"

PACKAGE_ARCH = "${TUNE_PKGARCH}"
COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${includedir}/amcodec
    cp -R --no-preserve=ownership ${S}/usr/include/amcodec/. ${D}${includedir}/amcodec/

    install -d ${D}${libdir}
    install -m 0755 ${S}/usr/lib/libamcodec.so.0.0 ${D}${libdir}/libamcodec.so.0.0
    ln -sf libamcodec.so.0.0 ${D}${libdir}/libamcodec.so
}

FILES:${PN} = "${libdir}/libamcodec.so*"
FILES:${PN}-dev = "${includedir}/amcodec"
FILES_SOLIBSDEV = ""

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped dev-so"
