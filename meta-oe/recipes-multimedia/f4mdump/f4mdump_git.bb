DESCRIPTION = "Simple F4M/HDS (Adobe HTTP Dynamic Streaming) dumper. Intended to be used by IPTVPlayer plugin: https://gitorious.org/iptv-pl-dla-openpli, http://iptvplayer.vline.pl."
MAINTAINER = "samsamsam"

FILESEXTRAPATHS_prepend := "${THISDIR}/$PN}:"

DEPENDS = "openssl zlib"
RDEPENDS_${PN} += "wget"

require conf/license/license-gplv2.inc
inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://gitlab.com/e2i/f4mdump.git;protocol=http \
        file://fix-build-openssl102q.patch"

S = "${WORKDIR}/git/"

SRCRTMP = "ext/librtmp/amf.c"
SRCRTMP =+ "ext/librtmp/hashswf.c"
SRCRTMP =+ "ext/librtmp/log.c"
SRCRTMP =+ "ext/librtmp/parseurl.c"
SRCRTMP =+ "ext/librtmp/rtmp.c"

SRCF4M = "src/b64.c"
SRCF4M =+ "src/F4mDownloader.cpp"
SRCF4M =+ "src/F4mProcessor.cpp"
SRCF4M =+ "src/ManifestParser.cpp"
SRCF4M =+ "src/RTMPTypes.cpp"
SRCF4M =+ "src/RTMPWrapper.cpp"
SRCF4M =+ "src/SimpleFunctions.cpp"
SRCF4M =+ "src/StreamReader.cpp"
SRCF4M =+ "src/StringHelper.cpp"
SRCF4M =+ "src/UdsDownloader.cpp"
SRCF4M =+ "src/console.cpp"
SRCF4M =+ "src/main.cpp"
SRCF4M =+ "src/parser.cpp"
SRCF4M =+ "src/tinyxml2.cpp"

do_compile () {
	rm -f *.o
	${CC} ${SRCRTMP} -c -fdata-sections -ffunction-sections -Os -Wall -Wl,--gc-sections -I${D}/${libdir} -I${D}/${includedir} -I${S}/ext/librtmp -lz ${LDFLAGS}
	${CXX} ${SRCF4M} -Os -Wno-narrowing -lssl -lcrypto -lz -std=c++0x -I${S}/inc -I${S}/ext -I${S}/ext/librtmp *.o -o f4mdump ${LDFLAGS}
	rm -f *.o
}

do_install () {
	install -d ${D}${bindir}
	install -m 0755 ${S}/f4mdump ${D}${bindir}
}
