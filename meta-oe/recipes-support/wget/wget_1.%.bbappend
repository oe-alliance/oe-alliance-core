FILESEXTRAPATHS_append := "${THISDIR}/wget"

SRC_URI_append = " \
           file://0002-Strip-long-version-output.patch \
          "

DEPENDS_remove = "gnutls"
DEPENDS_append = " openssl"
EXTRA_OECONF_remove = "--with-ssl=gnutls"
EXTRA_OECONF_append = " --with-ssl=openssl"

inherit upx-compress

# TODO: Temp-Fix for borked wget 1.19.2 -> Cheat in wget 1.19.3
# Remove when yocto switches to 1.19.3 or higher

PV = "1.19.3"
SRC_URI[md5sum] = "160e3164519a062d6492d5316a884d87"
SRC_URI[sha256sum] = "9801174275b4a47f85f5a3c2a99a84436cfe90815eafb5ee26c6100499528c76"

LIC_FILES_CHKSUM = "file://COPYING;md5=c678957b0c8e964aa6c70fd77641a71e"

# END TODO
