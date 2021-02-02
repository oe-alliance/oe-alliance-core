FILESEXTRAPATHS_append := "${THISDIR}/wget"

SRC_URI_append = " \
           file://0002-Strip-long-version-output.patch \
          "
SRC_URI_append_sh4 = " file://sh4-getrandom.patch"

DEPENDS_remove = "gnutls"
DEPENDS_append = " openssl"
EXTRA_OECONF_remove = "--with-ssl=gnutls"
EXTRA_OECONF_append = " --with-ssl=openssl"

CFLAGS_append_sh4 = " -std=gnu11"

inherit upx-compress
