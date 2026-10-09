SUMMARY = "Private DM9x0 VC5 scheduler fix for RetroArch"
DESCRIPTION = "Hash-verified libvc5dream copy; never replaces Enigma2's system library."
require conf/license/license-close.inc
PR = "r0"

# Reuse the exact hash-checked fix maintained for the Kodi DM9x0 port.
FILESEXTRAPATHS:prepend := "${THISDIR}/../../recipes-mediacenter/kodi/stb-kodi-22:"
SRC_URI = "file://patch-dm9x0-vc5-query.py"
S = "${UNPACKDIR}"
DEPENDS = "libvc5dream"
inherit python3native

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-dream-vc5', '.*', '^$', d)}"
RDEPENDS:${PN} = "libvc5dream"
PRIVATE_LIBS:${PN} = "libvc5dream.so.1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped ldflags"

do_install() {
    install -d ${D}${libdir}/retroarch/dm9x0
    ${PYTHON} ${UNPACKDIR}/patch-dm9x0-vc5-query.py \
        ${RECIPE_SYSROOT}${libdir}/libvc5dream.so.1.0.0 \
        ${D}${libdir}/retroarch/dm9x0/libvc5dream.so.1
    chmod 0755 ${D}${libdir}/retroarch/dm9x0/libvc5dream.so.1
}
FILES:${PN} = "${libdir}/retroarch/dm9x0/libvc5dream.so.1"
