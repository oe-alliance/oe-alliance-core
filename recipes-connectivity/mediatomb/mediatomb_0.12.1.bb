DESCRIPTION = "MediaTomb - UPnP AV MediaServer for Linux"
HOMEPAGE = "http://mediatomb.org/"
LICENSE = "GPLv2"
DEPENDS = "expat ffmpeg sqlite3 libexif js zlib file id3lib"
PV = "0.12.1"
PR = "r2"

SRC_URI[md5sum] = "e927dd5dc52d3cfcebd8ca1af6f0d3c2"
SRC_URI[sha256sum] = "31163c34a7b9d1c9735181737cb31306f29f1f2a0335fb4f53ecccf8f62f11cd"


SRC_URI = "${SOURCEFORGE_MIRROR}/mediatomb/mediatomb-${PV}.tar.gz \
		file://config.xml \
		file://init \
		"

S = "${WORKDIR}/mediatomb-${PV}"

CONFFILES_${PN} = "${sysconfdir}/mediatomb/config.xml"

INITSCRIPT_NAME = "mediatomb"
INITSCRIPT_PARAMS = "defaults 90 10"

inherit update-rc.d

inherit autotools pkgconfig

EXTRA_OECONF = "--disable-mysql \
                --disable-rpl-malloc \
		--enable-sqlite3 \
                --enable-libjs \
		--enable-libmagic \
		--enable-id3lib \
		--enable-libexif \
                --disable-largefile \
                --with-sqlite3-h=${STAGING_INCDIR} \
                --with-sqlite3-libs=${STAGING_LIBDIR} \
                --with-magic-h=${STAGING_INCDIR} \
                --with-magic-libs=${STAGING_LIBDIR} \
                --with-exif-h=${STAGING_INCDIR} \
                --with-exif-libs=${STAGING_LIBDIR} \
                --with-zlib-h=${STAGING_INCDIR} \
                --with-zlib-libs=${STAGING_LIBDIR} \
                --with-js-h=${STAGING_INCDIR}/js \
                --with-js-libs=${STAGING_LIBDIR} \
                --with-id3lib-h=${STAGING_INCDIR} \
                --with-id3lib-libs=${STAGING_LIBDIR}"

do_install() {
    oe_runmake 'DESTDIR=${D}' install
    install -d ${D}${sysconfdir}/mediatomb/
    install -m 755  ${WORKDIR}/config.xml ${D}${sysconfdir}/mediatomb/config.xml
    install -d ${D}${sysconfdir}/init.d/
    install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/${PN}
}
