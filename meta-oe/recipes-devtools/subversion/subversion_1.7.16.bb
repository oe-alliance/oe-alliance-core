SUMMARY = "Subversion (svn) version control system client"
SECTION = "console/network"
DEPENDS = "apr-util neon sqlite3 file"
RDEPENDS_${PN} = "neon"
LICENSE = "Apache-2"
HOMEPAGE = "http://subversion.tigris.org"

PR = "r4"

BBCLASSEXTEND = "native"

inherit gettext

SRC_URI = "${APACHE_MIRROR}/${BPN}/${BPN}-${PV}.tar.bz2 \
           file://libtool2.patch \
           file://fix-install-depends.patch \
           file://neon.m4-fix-includes-and-cflags.patch"

SRC_URI[md5sum] = "70d633fc6bd173b419c92d5e494919a5"
SRC_URI[sha256sum] = "9dcbd8622045c5a9ab381d70754304030f180967d6e3db694baaf708f3253238"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4a14fd2da3134e40a087eb4326a4ecd4"

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
