SUMMARY = "SpiderMonkey is Mozilla's JavaScript engine written in C/C++"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://../../LICENSE;md5=815ca599c9df247a0c7f619bab123dad"

SRC_URI = "http://ftp.mozilla.org/pub/mozilla.org/js/${PN}-${PV}.tar.bz2 \
    file://0001-js.pc.in-do-not-include-RequiredDefines.h-for-depend.patch"

SRC_URI[md5sum] = "5db79c10e049a2dc117a6e6a3bc78a8e"
SRC_URI[sha256sum] = "e62f3f331ddd90df1e238c09d61a505c516fe9fd8c5c95336611d191d18437d8"

PR = "r1"

S = "${WORKDIR}/${PN}-${PV}/js/src"

inherit autotools-brokensep pkgconfig perlnative

DEPENDS += "nspr"

# nspr's package-config is ignored so set libs manually
EXTRA_OECONF = " \
    --target=${TARGET_SYS} \
    --host=${BUILD_SYS} \
    --build=${BUILD_SYS} \
    --prefix=${prefix} \
    --with-nspr-libs='-lplds4 -lplc4 -lnspr4' \
    --enable-threadsafe \
    --libdir=${libdir} \
"

EXTRA_OECONF_append_sh4 = " \
    --enable-optimize=-O2 \
"

# mozjs requires autoreconf 2.13
do_configure() {
    ${S}/configure ${EXTRA_OECONF}
}

PACKAGES =+ "lib${PN}"
FILES_lib${PN} += "${libdir}/lib*.so"
FILES_${PN}-dev += "${bindir}/js17-config"
