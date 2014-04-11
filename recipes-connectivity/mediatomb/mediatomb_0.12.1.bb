SUMMARY = "MediaTomb - UPnP AV MediaServer for Linux"
HOMEPAGE = "http://mediatomb.org/"
LICENSE = "GPLv2"
DEPENDS = "expat ffmpeg sqlite3 libexif mozjs zlib file id3lib curl"

SRCREV = "${AUTOREV}"
PV = "0.12.1+git${SRCPV}"
PKGV = "0.12.1+git${GITPKGV}"
PR = "r4"

inherit gitpkgv autotools pkgconfig update-rc.d

LIC_FILES_CHKSUM = "file://COPYING;md5=0b609ee7722218aa600220f779cb5035"

SRC_URI = "git://git.code.sf.net/p/mediatomb/code.git;protocol=git \
        file://youtube_warning.patch \
        file://config.xml \
        file://init \
        "

S = "${WORKDIR}/git"

CONFFILES_${PN} = "${sysconfdir}/mediatomb/config.xml"

INITSCRIPT_NAME = "mediatomb"
INITSCRIPT_PARAMS = "defaults 90"

EXTRA_OECONF = "--disable-mysql \
    --disable-rpl-malloc \
    --enable-sqlite3 \
    --enable-libjs \
    --enable-libmagic \
    --enable-id3lib \
    --enable-libexif \
    --enable-inotify \
    --enable-db-autocreate \
    --disable-largefile \
    --with-sqlite3-h=${STAGING_INCDIR} \
    --with-sqlite3-libs=${STAGING_LIBDIR} \
    --with-magic-h=${STAGING_INCDIR} \
    --with-magic-libs=${STAGING_LIBDIR} \
    --with-exif-h=${STAGING_INCDIR} \
    --with-exif-libs=${STAGING_LIBDIR} \
    --with-zlib-h=${STAGING_INCDIR} \
    --with-zlib-libs=${STAGING_LIBDIR} \
    --with-js-h=${STAGING_INCDIR}/mozjs-24 \
    --with-js-libs=${STAGING_LIBDIR} \
    --with-id3lib-h=${STAGING_INCDIR} \
    --with-id3lib-libs=${STAGING_LIBDIR} \
    --with-ffmpeg-h=${STAGING_INCDIR} \
    --with-ffmpeg-libs=${STAGING_LIBDIR} \
    --with-search=${STAGING_DIR_HOST}${prefix}/local \
    ac_cv_header_sys_inotify_h=yes"

do_install() {
    oe_runmake 'DESTDIR=${D}' install
    install -d ${D}${sysconfdir}/mediatomb/
    install -m 755  ${WORKDIR}/config.xml ${D}${sysconfdir}/mediatomb/config.xml
    install -d ${D}${sysconfdir}/init.d/
    install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/${PN}
}
