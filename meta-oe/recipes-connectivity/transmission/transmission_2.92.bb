require transmission.inc

SRC_URI += "file://configure-kill-intl-check.patch"

OE_EXTRACONF = "\
    --disable-gtk --without-gtk \
    --disable-nls --without-nls \
    --disable-cli \
    --disable-mac \
    --disable-wx \
    --disable-beos \
    --enable-lightweight \
    --enable-daemon \
    CPPFLAGS=-DTR_EMBEDDED \
    "

LIC_FILES_CHKSUM = "file://COPYING;md5=0dd9fcdc1416ff123c41c785192a1895"
SRC_URI[archive.md5sum] = "17a6475d8f54717828222155f4cd4c6d"
SRC_URI[archive.sha256sum] = "d6a44ce9086b790b2469e2e30c854c2ecbd620ba93aeb394d59fec8c19d671ab"
