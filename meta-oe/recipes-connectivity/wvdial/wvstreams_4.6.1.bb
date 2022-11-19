HOMEPAGE = "http://alumnit.ca/wiki/index.php?page=WvStreams"
SUMMARY = "WvStreams is a network programming library in C++"

LICENSE = "LGPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=55ca817ccb7d5b5b66355690e9abc605"

DEPENDS = "zlib openssl (>= 0.9.8) dbus readline virtual/crypt"
RDEPENDS:libwvstreams-base += "libxcrypt-compat"

SRC_URI = "https://storage.googleapis.com/google-code-archive-downloads/v2/code.google.com/wvstreams/${BP}.tar.gz \
        file://wvstreams-4.2.2-multilib.patch \
        file://wvstreams-4.5-noxplctarget.patch \
        file://wvstreams-4.6.1-make.patch \
        file://wvstreams-4.6.1-statinclude.patch \
        file://wvstreams-4.6.1-gcc47.patch \
        file://wvstreams-4.6.1-magic.patch \
        file://wvstreams-4.6.1-bool.patch \
        file://0001-Check-for-limits.h-during-configure.patch \
        file://0001-build-fix-parallel-make.patch \
        file://0002-wvrules.mk-Use-_DEFAULT_SOURCE.patch \
        file://openssl-buildfix.patch \
        file://04_signed_request.diff \
        file://0001-Forward-port-to-OpenSSL-1.1.x.patch \
        "


SRC_URI[md5sum] = "2760dac31a43d452a19a3147bfde571c"
SRC_URI[sha256sum] = "8403f5fbf83aa9ac0c6ce15d97fd85607488152aa84e007b7d0621b8ebc07633"

inherit autotools-brokensep pkgconfig

TARGET_CFLAGS:append = " -fno-tree-dce -fno-optimize-sibling-calls"

LDFLAGS:append = " -Wl,-rpath-link,${CROSS_DIR}/${TARGET_SYS}/lib"

EXTRA_OECONF = " --without-tcl --without-qt --without-pam --without-valgrind"

CXXFLAGS:append = " -fno-strict-aliasing -fno-tree-dce -fno-optimize-sibling-calls -Wstrict-aliasing -Wno-narrowing"

PACKAGES:prepend = "libuniconf libuniconf-dbg "
PACKAGES:prepend = "uniconfd uniconfd-dbg "
PACKAGES:prepend = "libwvstreams-base libwvstreams-base-dbg "
PACKAGES:prepend = "libwvstreams-extras libwvstreams-extras-dbg "
PACKAGES:prepend = "${PN}-valgrind "

FILES:libuniconf     = "${libdir}/libuniconf.so.*"
FILES:libuniconf-dbg = "${libdir}/.debug/libuniconf.so.*"

FILES:uniconfd     = "${sbindir}/uniconfd ${sysconfdir}/uniconf.conf ${localstatedir}/uniconf"
FILES:uniconfd-dbg = "${sbindir}/.debug/uniconfd"

FILES:libwvstreams-base     = "${libdir}/libwvutils.so.*"
FILES:libwvstreams-base-dbg = "${libdir}/.debug/libwvutils.so.*"

FILES:libwvstreams-extras     = "${libdir}/libwvbase.so.* ${libdir}/libwvstreams.so.*"
FILES:libwvstreams-extras-dbg = "${libdir}/.debug/libwvbase.so.* ${libdir}/.debug/libwvstreams.so.*"

FILES:${PN}-valgrind = "${libdir}/valgrind/wvstreams.supp"

PACKAGES =+ "${PN}-testrun"
FILES:${PN}-testrun = "${bindir}/wvtestrun"
RDEPENDS:${PN}-testrun:prepend = "${PN} perl "
