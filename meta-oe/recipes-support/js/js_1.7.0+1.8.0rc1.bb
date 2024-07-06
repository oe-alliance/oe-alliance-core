DESCRIPTION = "Spidermonkey: a javascript engine written in C"
HOMEPAGE = "http://www.mozilla.org/js/spidermonkey/"
SECTION = "libs"

# the package is licensed under either of the following
LICENSE = "MPL-1.0 | GPL-2.0-or-later | LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://jsapi.c;beginline=4;endline=39;md5=347c6bbf4fb4547de1fa5ad830030063"

SRC_URI = "http://ftp.mozilla.org/pub/mozilla.org/js/js-1.8.0-rc1.tar.gz \
           file://link_with_gcc.patch \
           file://usepic.patch \
           file://buildcc.patch;striplevel=2 \
           file://fix-compile-with-gcc14.patch \
           file://fix-conflicting-types-gcc14.patch \
           file://jsautocfg.h \
           file://configure.ac"

SRC_URI[md5sum] = "eaad8815dcc66a717ddb87e9724d964e"
SRC_URI[sha256sum] = "374398699ac3fd802d98d642486cf6b0edc082a119c9c9c499945a0bc73e3413"

S = "${WORKDIR}/js/src"

# use local autoconf script to generate a usable jsautocfg.h
# don't bother with automake
inherit autotools-brokensep

do_extraunpack() {
	cp -f ${UNPACKDIR}/configure.ac ${S}
	cp -f ${UNPACKDIR}/jsautocfg.h ${S}
}
addtask extraunpack before do_configure after do_unpack

EXTRA_OEMAKE = "'CC=${CC}' 'LD=${LD}' 'XCFLAGS=${CFLAGS}' 'XLDFLAGS=${LDFLAGS} -Wl,-soname=libjs' \
                'BUILD_CC=${BUILD_CC}' 'BUILD_CFLAGS=${BUILD_CFLAGS}' 'BUILD_LDFLAGS=${BUILD_LDFLAGS}'"

PARALLEL_MAKE = ""

do_compile() {
	oe_runmake -f Makefile.ref JS_EDITLINE=1 PREBUILT_CPUCFG=1 BUILD_OPT=1
}

do_install() {
	install -d ${D}${libdir}
	install -d ${D}${includedir}
	install -d ${D}${includedir}/js
	oe_libinstall -so -C Linux_All_OPT.OBJ libjs ${D}${libdir}
	install -m 0644 ${S}/*.h ${D}${includedir}/js
	install -m 0644 ${S}/jsproto.tbl ${D}${includedir}/js
}

FILES_SOLIBSDEV = ""
FILES:${PN} = "${libdir}/lib*.so"

