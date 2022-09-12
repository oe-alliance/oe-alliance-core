FILESEXTRAPATHS:append := "${THISDIR}/wget"

SRC_URI:append = " \
           file://0002-Strip-long-version-output.patch \
          "

PACKAGE_NO_LOCALE = "1"
DEPENDS:remove = "gnutls"
DEPENDS:append = " openssl"
EXTRA_OECONF:remove = "--with-ssl=gnutls"
EXTRA_OECONF:append = " --with-ssl=openssl"

inherit upx-compress
