FILESEXTRAPATHS:append := "${THISDIR}/wget"

SRC_URI:append = " \
           file://0001-fix-2gb-download-limit-on-32bit.patch \
           file://0002-Strip-long-version-output.patch \
          "
SRC_URI:append:sh4 = " file://sh4-getrandom.patch"

PACKAGE_NO_LOCALE = "1"
DEPENDS:remove = "gnutls"
DEPENDS:append = " openssl"
EXTRA_OECONF:remove = "--with-ssl=gnutls"
EXTRA_OECONF:append = " --with-ssl=openssl"

CFLAGS:append:sh4 = " -std=gnu11"

inherit upx-compress
