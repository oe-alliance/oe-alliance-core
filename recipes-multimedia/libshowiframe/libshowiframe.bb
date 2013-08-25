DESCRIPTION = "libray to show an mpeg2/4 iframe on a linuxtv video device"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://showiframe.c;firstline=1;endline=1;md5=bd64286e61b76f76d236a6be21ecdaa9"

PV = "1.0"
PR = "r0"

SRC_URI = "file://showiframe.c file://showiframe.h"

S = "${WORKDIR}"
do_compile() {
	${CC} -fPIC -c showiframe.c
	${CC} -shared -Wl,-soname,libshowiframe.so.0 -o libshowiframe.so.0.0.0 showiframe.o
}

do_install() {
	install -d ${D}${libdir}
	install -m 755 libshowiframe.so.0.0.0 ${D}${libdir}/
	ln -s libshowiframe.so.0.0.0 ${D}${libdir}/libshowiframe.so.0
}

FILES_${PN} = "${libdir}"