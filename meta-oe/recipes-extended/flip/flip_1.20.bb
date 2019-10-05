SUMMARY = "File interchange program flip"
DESCRIPTION = "convert text file line endings between Unix and DOS formats \
 The program converts line endings of text files between MS-DOS and \
 *nix formats. It detects binary files in a nearly foolproof way and \
 leaves them alone unless you override this. It will also leave files \
 alone that are already in the right format and preserves file \
 timestamps. User interrupts are handled gracefully and no garbage or \
 corrupted files left behind. \
 . \
 The program does not convert files to a different character set, and \
 it can not handle old Apple Macintosh line endings that use CR only. \
 For that (and more), you can use the 'recode' program (package \
 'recode')."
MAINTAINER = "Jari Aalto <jari.aalto@cante.net>"
SECTION = "console/utils"
LICENSE = "GPL-2+"

FILESEXTRAPATHS_prepend := "${THISDIR}/debian:"

LIC_FILES_CHKSUM = "file://../copyright;md5=b6e7f553d5b9c366d42bfceaf296636f"

PV = "1.20"

SRC_URI = "http://http.debian.net/debian/pool/main/f/flip/flip_${PV}.orig.tar.gz \
           file://10-makefile.patch \
           file://20-manpage.patch \
           file://30-stdin.patch \
           file://copyright"

SRC_URI[md5sum] = "beaf2ec0c986fec9862539da72fbbc62"
SRC_URI[sha256sum] = "4cd45e581c71d7bcf1ab824a47fb9263fe5371ce702879a7d2efa08d27253471"

do_compile() {
	cd ${S}
	${CC} -D_FORTIFY_SOURCE=2 -g -O2 -fPIE -fstack-protector-strong -Wformat -Werror=format-security -Wall -pedantic -DBSD -DIX -DSTDINCLUDE -c -DNDEBUG -O -DVERSION=\"1.20\" flip.c
	${CC} -fPIE -pie -Wl,-z,relro -Wl,-z,now -Wl,--as-needed flip.o -o flip
}

do_install() {
	cd ${S}
	gzip flip.1
	install -d ${D}/usr/bin
	install -m 755 ${S}/flip ${D}/usr/bin
	cd ${D}/usr/bin && { ln -s flip toix; ln -s flip toms; }

	install -d ${D}/usr/share/man/man1
	install -m 644 ${S}/flip.1.gz ${D}/usr/share/man/man1
	cd ${D}/usr/share/man/man1 && { ln -s flip.1.gz toix.1.gz; ln -s flip.1.gz toms.1.gz; }
}

INSANE_SKIP_${PN} += "ldflags"
