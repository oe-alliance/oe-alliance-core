SUMMARY = "network auditing tool"
DESCRIPTION = "Nmap ("Network Mapper") is a free and open source (license) utility for network discovery and security auditing.\nGui support via appending to IMAGE_FEATURES x11-base in local.conf"
SECTION = "security"
LICENSE = "GPL-2.0"

LIC_FILES_CHKSUM = "file://COPYING;beginline=7;endline=12;md5=bce7593e567a4b12f60c6a04f9b8c1e5"

SRC_URI = "http://nmap.org/dist/${BP}.tar.bz2"

SRC_URI[md5sum] = "f2f6660142a777862342a58cc54258ea"
SRC_URI[sha256sum] = "cb9f4e03c0771c709cd47dc8fc6ac3421eadbdd313f0aae52276829290583842"


inherit autotools-brokensep pkgconfig python-dir distro_features_check upx-compress
DEPENDS = "libpcap"

#PACKAGECONFIG ?= "ncat nping ndiff pcap lua"
PACKAGECONFIG ?= ""

PACKAGECONFIG[pcap] = "--with-pcap=linux, --without-pcap, libpcap, libpcap"
PACKAGECONFIG[pcre] = "--with-libpcre=${STAGING_LIBDIR}/.., --with-libpcre=included, libpre"
PACKAGECONFIG[ssl] = "--with-openssl=${STAGING_LIBDIR}/.., --without-openssl, openssl, openssl"

#disable/enable packages
PACKAGECONFIG[nping] = ",--without-nping,"
PACKAGECONFIG[ncat] = ",--without-ncat,"
PACKAGECONFIG[ndiff] = ",--without-ndiff,"
PACKAGECONFIG[update] = ",--without-nmap-update,"
PACKAGECONFIG[lua] = "--with-liblua=included,--without-liblua"

EXTRA_OECONF = "--with-libdnet=included --with-liblinear=included --without-subversion --without-zenmap"

PACKAGES = "${PN} ${PN}-dbg ${PN}-doc ${PN}-db"

FILES_${PN} = "${bindir}/nmap ${datadir}/nmap/nmap.xsl"
FILES_${PN}-db = "${datadir}/nmap/*"
DESCRIPTION_${PN}-db = "Databases for translation of numeric values into friendly descriptions"

# append packages if enabled
FILES_${PN} += "${@bb.utils.contains("PACKAGECONFIG", "ncat", "${bindir}/ncat ${target_datadir}/ncat", "", d)}"
FILES_${PN} += "${@bb.utils.contains("PACKAGECONFIG", "nping", "${bindir}/nping", "", d)}"
FILES_${PN} += "${@bb.utils.contains("PACKAGECONFIG", "ndiff", "${bindir}/ndiff ${bindir}/uninstall_ndiff ${libdir}/python${PYTHON_BASEVERSION}/site-packages/ndiff.py*", "", d)}"

do_configure() {
    # strip hard coded python2#
    sed -i -e 's=python2\.*=python=g'  ${S}/configure.ac
    sed -i -e 's=python2\.*=python=g'  ${S}/configure
    autoconf
    oe_runconf
}

do_install_append () {
   # remove python dir, its not used or installed
   rm -fr ${D}/${libdir}
}

RDEPENDS_${PN} = "python-core"
RDEPENDS_${PN}-db = "nmap"
