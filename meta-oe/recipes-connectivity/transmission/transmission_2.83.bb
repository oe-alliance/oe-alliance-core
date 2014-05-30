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

SRC_URI[archive.md5sum] = "4620cfbfefee2ce55a6fa12c3ec330a7"
SRC_URI[archive.sha256sum] = "b0e1b050167e7f71b68e01a8d55b984a828fe880df9abfbc6281cb2a0d7d1433"
                          