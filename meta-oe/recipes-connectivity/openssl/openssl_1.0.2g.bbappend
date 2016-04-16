FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR .= ".2"

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI_append_arm = " file://fix_openssl_100_version.diff"
