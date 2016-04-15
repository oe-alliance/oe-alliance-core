FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR .= ".1"

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI += " \
    file://fix_openssl_100_version.diff \
"
