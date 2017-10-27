SUMMARY = "GSM Audio Library"
SECTION = "libs"
PRIORITY = "optional"
#DEPENDS = ""
LICENSE = "libgsm"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=97e265fa1fd10a668bd99c4945fb9200"


SRC_URI = "http://user.cs.tu-berlin.de/~jutta/gsm/gsm-${PV}.tar.gz \
    file://01_makefile.patch;patch=1 \
    file://02_cplusplus.patch;patch=1 \
    file://03_config.patch;patch=1 \
    file://04_includes.patch;patch=1 \
    file://05_compiler_warnings.patch;patch=1 \
    "

S = "${WORKDIR}/gsm-1.0-pl12/"

CFLAGS += "-c -g -fPIC -Wall -D_GNU_SOURCE -D_REENTRANT -DNeedFunctionPrototypes=1 -DWAV49 -I./inc"

PARALLEL_MAKE = ""

do_compile() {
    unset LD
    oe_runmake CCFLAGS="${CFLAGS}"
}

do_install() {
    oe_libinstall -a -C lib libgsm ${D}${libdir}
    oe_libinstall -so -C lib libgsm ${D}${libdir}
    install -d ${D}${includedir}/gsm
    install -m 0644 ${S}/inc/gsm.h ${D}${includedir}/gsm/
    cd ${D}${includedir}
    ln -s gsm/gsm.h gsm.h
}