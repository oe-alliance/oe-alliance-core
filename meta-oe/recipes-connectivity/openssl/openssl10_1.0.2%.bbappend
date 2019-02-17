inherit upx-compress

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"


PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI_append = " file://fix_openssl_100_version.patch \
    file://fix_openssl_100_version_jethro.patch \
"

RPROVIDES_libcrypto10 ="libcrypto"
RPROVIDES_libssl10 ="libssl"
RPROVIDES_openssl-conf10 ="openssl-conf"
RPROVIDES_${PN} ="openssl"

PROVIDES += "libcrypto libssl openssl-conf openssl"

openssl_package_preprocess () {
    :
}
