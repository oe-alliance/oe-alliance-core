SUMMARY = "libray to show an mpeg2/4 iframe on a linuxtv video device"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://showiframe.c;firstline=1;endline=1;md5=22919e57c6dcf1ff48ac50e784f44880"

PV = "1.0"

PROVIDES =+ " libshowiframe0"
PACKAGES =+ " libshowiframe0"

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

INSANE_SKIP_${PN} += "ldflags"