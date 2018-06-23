FILESEXTRAPATHS_append := "${THISDIR}/wget"

SRC_URI_append = " \
           file://0002-Strip-long-version-output.patch \
          "

DEPENDS_remove = "gnutls"
DEPENDS_append = " openssl"
EXTRA_OECONF_remove = "--with-ssl=gnutls"
EXTRA_OECONF_append = " --with-ssl=openssl"

inherit upx-compress

PV = "1.19.5"

SRC_URI[md5sum] = "2db6f03d655041f82eb64b8c8a1fa7da"
SRC_URI[sha256sum] = "b39212abe1a73f2b28f4c6cb223c738559caac91d6e416a6d91d4b9d55c9faee"

LIC_FILES_CHKSUM = "file://COPYING;md5=c678957b0c8e964aa6c70fd77641a71e"
