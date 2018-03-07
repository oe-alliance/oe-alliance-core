inherit upx_compress

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"


PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI_append = " file://fix_openssl_100_version.patch \
    file://fix_openssl_100_version_jethro.patch \
"
