SUMMARY = "Meta package for installing all dependencies for SSS' E2iPlayer"
MAINTAINER = "samsamsam"
require conf/license/license-gplv2.inc

inherit gitpkgv ${PYTHON_PN}-dir

DEPENDS = "${PYTHON_PN} curl ffmpeg"
RRECOMMENDS:${PN} = " \
    ffmpeg \
    exteplayer3 \
    gstplayer \
    wget \
    f4mdump \
    gst-ifdsrc \
    rtmpdump \
    duktape \
    uchardet \
    "

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r2"

SRC_URI = "git://github.com/oe-mirrors/e2iplayer-deps.git;protocol=git"

S1 = "${WORKDIR}/git/e2isubparser"
SOURCE_FILES1 = "src/subparsermodule.c"
SOURCE_FILES1 =+ "src/vlc/src/subtitle.c"
SOURCE_FILES1 =+ "src/ffmpeg/src/htmlsubtitles.c"
SOURCE_FILES1 =+ "src/expat-2.2.0/xmlparse.c"
SOURCE_FILES1 =+ "src/expat-2.2.0/xmlrole.c"
SOURCE_FILES1 =+ "src/expat-2.2.0/xmltok.c"
SOURCE_FILES1 =+ "src/expat-2.2.0/xmltok_impl.c"
SOURCE_FILES1 =+ "src/expat-2.2.0/xmltok_ns.c"
SOURCE_FILES1 =+ "src/ttml/src/ttmlparser.c"
SOURCE_FILES1 =+ "src/html/src/htmlcleaner.c"

S2 = "${WORKDIR}/git/hlsdl"
SOURCE_FILES2 = "src/main.c"
SOURCE_FILES2 =+ "src/aes_openssl.c"
SOURCE_FILES2 =+ "src/curl.c"
SOURCE_FILES2 =+ "src/hls.c"
SOURCE_FILES2 =+ "src/misc.c"
SOURCE_FILES2 =+ "src/msg.c"
SOURCE_FILES2 =+ "src/mpegts.c"

S3 = "${WORKDIR}/git/lsdir"
SOURCE_FILES3 = "src/lsdir.c"

S4 = "${WORKDIR}/git/cmdwrap"
SOURCE_FILES4 = "src/cmdwrap.c"

do_compile() {
    cd ${S1}
    ${CC} ${SOURCE_FILES1} -shared -pipe -D_FILE_OFFSET_BITS=64 -D_LARGEFILE64_SOURCE -D_LARGEFILE_SOURCE -D_GNU_SOURCE=1 -DNDEBUG -Os -shared -Wall -Wstrict-prototypes -fPIC -DMAJOR_VERSION=0 -DMINOR_VERSION=2 -DHAVE_EXPAT_CONFIG_H -I${S1}/src -I${S1}/src/vlc/include -I${S1}/src/ffmpeg/include -I${S1}/src/expat-2.2.0 -I${S1}/src/ttml/include -I${S1}/src/html/include -I${D}/${libdir} -I${D}/${includedir} -I${STAGING_DIR_TARGET}/${includedir}/${PYTHON_DIR} -lm -l${PYTHON_DIR} -o _subparser.so -Wl,--gc-sections ${LDFLAGS}
    cd ${S2}
    ${CC} ${SOURCE_FILES2} -D_FILE_OFFSET_BITS=64 -D_LARGEFILE64_SOURCE -D_LARGEFILE_SOURCE -D_GNU_SOURCE=1 -DWITH_FFMPEG -Wall -Wstrict-prototypes -Wmissing-prototypes -Wmissing-declarations -Wno-deprecated-declarations -Wshadow -Wpointer-arith -Wcast-qual -Wsign-compare -DPREFIX="/usr" -std=gnu99 -I${S2}/src -I${D}/${libdir} -I${D}/${includedir} -lrt -lpthread -lz -lssl -lcrypto -lcurl -lavcodec -lavformat -lavutil -o hlsdl ${LDFLAGS}
    cd ${S3}
    ${CC} ${SOURCE_FILES3} -D_FILE_OFFSET_BITS=64 -D_LARGEFILE64_SOURCE=1 -D_LARGEFILE_SOURCE -I${S3}/src -I${D}/${libdir} -I${D}/${includedir} -o lsdir ${LDFLAGS}
    cd ${S4}
    ${CC} ${SOURCE_FILES4} -D_FILE_OFFSET_BITS=64 -D_LARGEFILE64_SOURCE=1 -D_LARGEFILE_SOURCE -I${S4}/src -I${D}/${libdir} -I${D}/${includedir} -o cmdwrap ${LDFLAGS}
}

do_install() {
    install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/IPTVPlayer/libs/iptvsubparser
    install -m 0777 ${S1}/_subparser.so ${D}${libdir}/enigma2/python/Plugins/Extensions/IPTVPlayer/libs/iptvsubparser/
    install -d ${D}${bindir}
    install -m 0755 ${S2}/hlsdl ${D}${bindir}/
    install -m 0755 ${S3}/lsdir ${D}${bindir}/
    install -m 0755 ${S4}/cmdwrap ${D}${bindir}/
}

FILES:${PN} = "${libdir}/enigma2/python/Plugins/Extensions/IPTVPlayer/libs/iptvsubparser ${bindir}"
