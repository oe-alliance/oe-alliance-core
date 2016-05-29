FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR .= ".4"

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI_append = " file://fix_openssl_100_version.patch"
