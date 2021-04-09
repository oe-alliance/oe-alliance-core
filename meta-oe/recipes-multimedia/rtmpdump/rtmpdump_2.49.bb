SUMMARY = "RTMP Dump"
DESCRIPTION = "rtmpdump is a toolkit for RTMP streams. All forms of RTMP are \
supported, including rtmp://, rtmpt://, rtmpe://, rtmpte://, and rtmps://."
HOMEPAGE = "http://rtmpdump.mplayerhq.hu/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

PROVIDES += "librtmp librtmp1"
RPROVIDES_${PN} += "librtmp librtmp1"

DEPENDS = "gnutls zlib openssl"

SRCREV = "f1b83c10d8beb43fcc70a6e88cf4325499f25857"
SRC_URI = " \
	git://git.ffmpeg.org/rtmpdump;protocol=git \
	file://0001-KSV-patch-2015-12-15.patch \
	file://0002-fix-build-openssl102q.patch \
	file://0003-add-movecast-thx-testi.patch \
	"

S = "${WORKDIR}/git"

inherit autotools-brokensep

EXTRA_OEMAKE = " \
    CC='${CC}' LD='${LD} ${STAGING_LIBDIR}' XCFLAGS='${CFLAGS}' XLDFLAGS='${LDFLAGS}' \
    SYS=posix INC=-I=/usr/include DESTDIR=${D} CRYPTO=GNUTLS \
    prefix=${prefix} libdir=${libdir} incdir=${includedir}/librtmp bindir=${bindir} mandir=${mandir}"

INSANE_SKIP_${PN} += "ldflags"
