FILESEXTRAPATHS:append := "${THISDIR}/wget"

PR:append = ".1"

SRC_URI:append = " \
           file://0002-Strip-long-version-output.patch \
           file://openssl40-removed-methods.patch \
          "

do_install:append() {
    printf '\nprefer-family = IPv4\n' >> "${D}${sysconfdir}/wgetrc"
}  

PACKAGE_NO_LOCALE = "1"
DEPENDS:remove = "gnutls"
DEPENDS:append = " openssl"
EXTRA_OECONF:remove = "--with-ssl=gnutls"
EXTRA_OECONF:append = " --with-ssl=openssl"

inherit upx-compress
