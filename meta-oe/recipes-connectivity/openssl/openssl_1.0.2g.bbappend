FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR .= ".3"

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI = " file://fix_openssl_100_version.diff"
