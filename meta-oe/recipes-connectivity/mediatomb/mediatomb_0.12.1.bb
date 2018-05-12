SUMMARY = "MediaTomb - UPnP AV MediaServer for Linux"
HOMEPAGE = "http://mediatomb.org/"
LICENSE = "GPLv2"
DEPENDS = "expat ffmpeg sqlite3 libexif js zlib file id3lib curl"
PV = "0.12.1"
PR = "r7"

LIC_FILES_CHKSUM = "file://COPYING;md5=0b609ee7722218aa600220f779cb5035"

SRC_URI[md5sum] = "e927dd5dc52d3cfcebd8ca1af6f0d3c2"
SRC_URI[sha256sum] = "31163c34a7b9d1c9735181737cb31306f29f1f2a0335fb4f53ecccf8f62f11cd"


SRC_URI = "${SOURCEFORGE_MIRROR}/mediatomb/mediatomb-${PV}.tar.gz \
        file://youtube_warning.patch \
        file://libav_0.7_support.patch \
        file://libavformat_0.11_support.patch \
        file://config.xml \
        file://init \
        file://fix_hash_compile_errors.patch \
        "

SRC_URI_append_aarch64 = " file://mediatomb_aarch64.patch "

S = "${WORKDIR}/mediatomb-${PV}"

CONFFILES_${PN} = "${sysconfdir}/mediatomb/config.xml"

INITSCRIPT_NAME = "mediatomb"
INITSCRIPT_PARAMS = "defaults 90"

inherit autotools-brokensep pkgconfig update-rc.d

EXTRA_OECONF = "--disable-mysql \
                --disable-rpl-malloc \
                --enable-sqlite3 \
                --enable-libjs \
                --enable-libmagic \
                --enable-id3lib \
                --enable-libexif \
                --enable-inotify \
                --enable-db-autocreate \
                --enable-largefile \
                --with-sqlite3-h=${STAGING_INCDIR} \
                --with-sqlite3-libs=${STAGING_LIBDIR} \
                --with-zlib-h=${STAGING_INCDIR} \
                --with-zlib-libs=${STAGING_LIBDIR} \
                --with-js-h=${STAGING_INCDIR}/js \
                --with-js-libs=${STAGING_LIBDIR} \
                --with-id3lib-h=${STAGING_INCDIR} \
                --with-id3lib-libs=${STAGING_LIBDIR} \
                --with-search=${STAGING_DIR_HOST}${prefix}/local \
                ac_cv_header_sys_inotify_h=yes"

do_install() {
    oe_runmake 'DESTDIR=${D}' install
    install -d ${D}${sysconfdir}/mediatomb/
    install -m 755  ${WORKDIR}/config.xml ${D}${sysconfdir}/mediatomb/config.xml
    install -d ${D}${sysconfdir}/init.d/
    install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/${PN}
}
