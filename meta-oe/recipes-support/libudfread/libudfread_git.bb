SUMMARY  = "UDF reader"
SECTION = "libs"
HOMEPAGE = "http://videolan.org"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.2.0+git"
PKGV = "1.2.0+git${GITPKGV}"

SRC_URI = "git://github.com/oe-mirrors/libudfread.git;protocol=https;branch=master"

inherit meson pkgconfig

pkg_postinst:${PN}:append () {
if [ -z "$D" ]; then
    if [ ! -e "${libdir}/libudfread.so.0" ] && [ ! -L "${libdir}/libudfread.so.0" ]; then
        sofile="$(basename "$(readlink -f ${libdir}/libudfread.so.*.*.* || true)")"
        if [ -n "$sofile" ]; then
            ln -s "$sofile" "${libdir}/libudfread.so.0"
        fi
    fi
fi
}
