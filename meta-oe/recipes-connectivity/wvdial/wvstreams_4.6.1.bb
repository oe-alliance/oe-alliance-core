HOMEPAGE = "http://alumnit.ca/wiki/index.php?page=WvStreams"
SUMMARY = "WvStreams is a network programming library in C++"

LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=55ca817ccb7d5b5b66355690e9abc605"

DEPENDS = "zlib openssl (>= 0.9.8) dbus readline virtual/crypt"
RDEPENDS_libwvstreams-base += "libxcrypt-compat"

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

SRC_URI[md5sum] = "d4a0e7f4375aead34e6569edb9ad6d1e"
SRC_URI[sha256sum] = "864596d93013c9608b48f16fc9f976addf7186926d36f783979b79bebb3ac704"

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

PACKAGES =+ "${PN}-testrun"
FILES_${PN}-testrun = "${bindir}/wvtestrun"
RDEPENDS_${PN}-testrun_prepend = "${PN} perl "
