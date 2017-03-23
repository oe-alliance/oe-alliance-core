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
SRC_URI[archive.md5sum] = "e4190f727dc1c14d1998693262eb693a"
SRC_URI[archive.sha256sum] = "825474d1296061cec9642a4d677c7bb5ff0caad565e5edf916622ad614a15443"
