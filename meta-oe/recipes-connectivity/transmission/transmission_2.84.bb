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

LIC_FILES_CHKSUM = "file://COPYING;md5=a1923fe8f8ff37c33665716af0ec84f1"
SRC_URI[archive.md5sum] = "411aec1c418c14f6765710d89743ae42"
SRC_URI[archive.sha256sum] = "a9fc1936b4ee414acc732ada04e84339d6755cd0d097bcbd11ba2cfc540db9eb"
