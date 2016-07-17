FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR .= ".6"

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI_append = " file://fix_openssl_100_version.patch \
    file://fix_openssl_100_version_jethro.patch \
"
