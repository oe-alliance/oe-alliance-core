SUMMARY = "Subversion (svn) version control system client"
SECTION = "console/network"
DEPENDS = "apr-util neon sqlite3 file"
RDEPENDS_${PN} = "neon"
LICENSE = "Apache-2"
HOMEPAGE = "http://subversion.tigris.org"

PR = "r3"

BBCLASSEXTEND = "native"

inherit gettext

SRC_URI = "${APACHE_MIRROR}/${BPN}/${BPN}-${PV}.tar.bz2 \
           file://libtool2.patch"

SRC_URI[md5sum] = "db04a587c68e38da6d0e342e6508f514"
SRC_URI[sha256sum] = "1569931cd85ae27052895554bc14125e0795d7a17d3e4a7c2aceb7289be71db3"

LIC_FILES_CHKSUM = "file://LICENSE;md5=1c2f0119e478700b5428e26386cff923"

PACKAGECONFIG[sasl] = "--with-sasl,--without-sasl,cyrus-sasl"

EXTRA_OECONF = " \
                --without-berkeley-db --without-apxs \
                --without-swig --with-apr=${STAGING_BINDIR_CROSS} \
                --with-apr-util=${STAGING_BINDIR_CROSS} \
                ac_cv_path_RUBY=none"

inherit autotools

export LDFLAGS += " -L${STAGING_LIBDIR} "

acpaths = "-I build/ -I build/ac-macros/"

do_configure_prepend () {
	rm -f ${S}/libtool
	rm -f ${S}/build/libtool.m4 ${S}/build/ltmain.sh ${S}/build/ltoptions.m4 ${S}/build/ltsugar.m4 ${S}/build/ltversion.m4 ${S}/build/lt~obsolete.m4
	rm -f ${S}/aclocal.m4
	sed -i -e 's:with_sasl="/usr/local":with_sasl="${STAGING_DIR}":' ${S}/build/ac-macros/sasl.m4
}
