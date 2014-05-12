SUMMARY = "Nmap is a command line portscanner."
HOMEPAGE = "http://www.insecure.org/nmap/"
SECTION = "console/network"

require conf/license/license-gplv2.inc

DEPENDS = "libpcap libcap libpcre"
PR = "r4"

inherit autotools-brokensep

SRC_URI = "http://download.insecure.org/nmap/dist-old/nmap-${PV}.tar.bz2 \
       file://autofoo.patch \
       file://remove_gtk.patch"
S = "${WORKDIR}/nmap-${PV}"

EXTRA_OECONF = "--with-pcap=linux \
        --with-libpcap=${STAGING_LIBDIR}/.. \
        --with-libpcre=${STAGING_LIBDIR}/.. \
        --without-nmapfe \
        --without-openssl"
EXTRA_OEMAKE = "STRIPPROG=${STRIP}"

PARALLEL_MAKE = ""

CXXFLAGS_append = " -fpermissive"
# Ugly hack follows -- their configure.ac doesnt match their configure ..
# doesnt include a check for the length type in recvfrom, so we hack it here
CPPFLAGS_append = " -Drecvfrom6_t=socklen_t"

do_install () {
    oe_runmake 'prefix=${D}${prefix}' \
           'exec_prefix=${D}${exec_prefix}' \
           'bindir=${D}${bindir}' \
           'sbindir=${D}${sbindir}' \
           'mandir=${D}${mandir}' \
           'datadir=${D}${datadir}' \
           'nmapdatadir=${D}${datadir}/nmap' \
           install
}

SRC_URI[md5sum] = "0713306dda85aee2c95ef31b4b7d2838"
SRC_URI[sha256sum] = "1cfb120008f636a874b871f1625409082badaaf64177c9ac873480630390356c"
