FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://py-compile-compile-only-optimized-byte-code.patch \
    file://py-compile-fail-on-error.patch \
"
