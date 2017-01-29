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
SRC_URI[archive.md5sum] = "3fce404a436e3cd7fde80fb6ed61c264"
SRC_URI[archive.sha256sum] = "3a8d045c306ad9acb7bf81126939b9594553a388482efa0ec1bfb67b22acd35f"
