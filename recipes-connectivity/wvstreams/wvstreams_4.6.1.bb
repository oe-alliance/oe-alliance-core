HOMEPAGE = "http://alumnit.ca/wiki/index.php?page=WvStreams"

require conf/license/license-gplv2.inc

LICENSE = "GPL"
DESCRIPTION = "WvStreams is a network programming library in C++"
DEPENDS = "zlib openssl (>= 0.9.8)"

PR = "r0"

SRC_URI = "http://wvstreams.googlecode.com/files/${PN}-${PV}.tar.gz \
	file://0001-Fixed-missing-header-includes.patch \
	"

inherit autotools pkgconfig

LDFLAGS_append = " -Wl,-rpath-link,${TOOLCHAIN_PATH}/${TARGET_SYS}/lib"

# dbus detection currently broken in configure.ac; remember to add "dbus (>= 1.2.14)" to DEPENDS  when fixed
EXTRA_OECONF = " --without-tcl --without-qt --without-pam --without-dbus"

PACKAGES_prepend = "libuniconf libuniconf-dbg "
PACKAGES_prepend = "uniconfd uniconfd-dbg "
PACKAGES_prepend = "libwvstreams-base libwvstreams-base-dbg "
PACKAGES_prepend = "libwvstreams-extras libwvstreams-extras-dbg "

FILES_libuniconf     = "${libdir}/libuniconf.so.*"
FILES_libuniconf-dbg = "${libdir}/.debug/libuniconf.so.*"

FILES_uniconfd     = "${sbindir}/uniconfd ${sysconfdir}/uniconf.conf ${localstatedir}/uniconf"
FILES_uniconfd-dbg = "${sbindir}/.debug/uniconfd"

FILES_libwvstreams-base     = "${libdir}/libwvutils.so.*"
FILES_libwvstreams-base-dbg = "${libdir}/.debug/libwvutils.so.*"

FILES_libwvstreams-extras     = "${libdir}/libwvbase.so.* ${libdir}/libwvstreams.so.*"
FILES_libwvstreams-extras-dbg = "${libdir}/.debug/libwvbase.so.* ${libdir}/.debug/libwvstreams.so.*"

PARALLEL_MAKE = ""

do_configure() {
        autoreconf
        oe_runconf
}

SRC_URI[md5sum] = "2760dac31a43d452a19a3147bfde571c"
SRC_URI[sha256sum] = "8403f5fbf83aa9ac0c6ce15d97fd85607488152aa84e007b7d0621b8ebc07633"
