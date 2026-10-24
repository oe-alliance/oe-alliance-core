# Images built before the lib_package split shipped the library and tools in
# lcms. Keep that real package name for installed versioned dependencies such
# as python3-pillow: IPK deliberately drops versions from Provides entries.
PR:append = ".4"

PACKAGE_BEFORE_PN:append:class-target = " ${PN}-lib"
FILES:${PN}-lib = "${libdir}/liblcms2${SOLIBS}"
ALLOW_EMPTY:${PN}:class-target = "1"
FILES:${PN}:class-target = ""
RDEPENDS:${PN}:append:class-target = " ${PN}-lib (= ${EXTENDPKGV}) ${PN}-bin (= ${EXTENDPKGV})"

# Preserve the published Debian-style names after moving the shared library
# out of the main package, which now keeps the old combined package usable.
DEBIAN_NOAUTONAME:${PN}:class-target = "1"
PKG:${PN}-lib:class-target = "${MLPREFIX}liblcms2-2"
PKG:${PN}-bin:class-target = "${MLPREFIX}liblcms2-bin"
PKG:${PN}-dev:class-target = "${MLPREFIX}liblcms2-dev"
PKG:${PN}-dbg:class-target = "${MLPREFIX}liblcms2-dbg"
PKG:${PN}-doc:class-target = "${MLPREFIX}liblcms2-doc"
PKG:${PN}-src:class-target = "${MLPREFIX}liblcms2-src"
PKG:${PN}-staticdev:class-target = "${MLPREFIX}liblcms2-staticdev"
PKG:${PN}-ptest:class-target = "${MLPREFIX}liblcms2-ptest"

# Take over files only from older combined packages; the current compatibility
# package must coexist with the library and utilities.
RREPLACES:${PN}-lib = "${PN} (< ${EXTENDPKGV})"
RCONFLICTS:${PN}-lib = "${PN} (< ${EXTENDPKGV})"
RREPLACES:${PN}-bin:append:class-target = " ${PN} (< ${EXTENDPKGV})"
