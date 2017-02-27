HOMEPAGE = "http://alumnit.ca/wiki/index.php?page=WvStreams"
SUMMARY = "WvStreams is a network programming library in C++"

LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=55ca817ccb7d5b5b66355690e9abc605"

DEPENDS = "zlib openssl (>= 0.9.8) dbus readline"

SRC_URI = "http://${BPN}.googlecode.com/files/${BP}.tar.gz \
	  file://wvstreams-4.2.2-multilib.patch \
	  file://wvstreams-4.5-noxplctarget.patch \
	  file://wvstreams-4.6.1-make.patch \
	  file://wvstreams-4.6.1-statinclude.patch \
	  file://wvstreams-4.6.1-gcc.patch \ 
	  file://wvstreams-4.6.1-gcc47.patch \
	  file://wvstreams-4.6.1-magic.patch \
	  file://wvstreams-4.6.1-bool.patch \
          "

SRC_URI[md5sum] = "2760dac31a43d452a19a3147bfde571c"
SRC_URI[sha256sum] = "8403f5fbf83aa9ac0c6ce15d97fd85607488152aa84e007b7d0621b8ebc07633"

inherit autotools-brokensep pkgconfig

PARALLEL_MAKE = ""

LDFLAGS_append = " -Wl,-rpath-link,${CROSS_DIR}/${TARGET_SYS}/lib"

EXTRA_OECONF = " --without-tcl --without-qt --without-pam --without-valgrind"

CXXFLAGS_append = " -fno-strict-aliasing -fno-tree-dce -fno-optimize-sibling-calls -Wstrict-aliasing"

PACKAGES_prepend = "libuniconf libuniconf-dbg "
PACKAGES_prepend = "uniconfd uniconfd-dbg "
PACKAGES_prepend = "libwvstreams-base libwvstreams-base-dbg "
PACKAGES_prepend = "libwvstreams-extras libwvstreams-extras-dbg "
PACKAGES_prepend = "${PN}-valgrind "

FILES_libuniconf     = "${libdir}/libuniconf.so.*"
FILES_libuniconf-dbg = "${libdir}/.debug/libuniconf.so.*"

FILES_uniconfd     = "${sbindir}/uniconfd ${sysconfdir}/uniconf.conf ${localstatedir}/uniconf"
FILES_uniconfd-dbg = "${sbindir}/.debug/uniconfd"

FILES_libwvstreams-base     = "${libdir}/libwvutils.so.*"
FILES_libwvstreams-base-dbg = "${libdir}/.debug/libwvutils.so.*"

FILES_libwvstreams-extras     = "${libdir}/libwvbase.so.* ${libdir}/libwvstreams.so.*"
FILES_libwvstreams-extras-dbg = "${libdir}/.debug/libwvbase.so.* ${libdir}/.debug/libwvstreams.so.*"

FILES_${PN}-valgrind = "${libdir}/valgrind/wvstreams.supp"
