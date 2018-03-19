FILESEXTRAPATHS_append := "${THISDIR}/wget"

SRC_URI_append = " \
           file://0002-Strip-long-version-output.patch \
          "

DEPENDS_remove = "gnutls"
DEPENDS_append = " openssl"
EXTRA_OECONF_remove = "--with-ssl=gnutls"
EXTRA_OECONF_append = " --with-ssl=openssl"

inherit upx-compress

# TODO: Temp-Fix for borked wget 1.19.2 -> Cheat in wget 1.19.4
# Remove when yocto switches to 1.19.3 or higher

PV = "1.19.4"
SRC_URI[md5sum] = "a2a2c1dc4ac5003fc25a8e60b4a9464e"
SRC_URI[sha256sum] = "93fb96b0f48a20ff5be0d9d9d3c4a986b469cb853131f9d5fe4cc9cecbc8b5b5"

LIC_FILES_CHKSUM = "file://COPYING;md5=c678957b0c8e964aa6c70fd77641a71e"

# END TODO
