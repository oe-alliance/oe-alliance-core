SUMMARY = "Provides web pages for the rescue partition"
HOMEPAGE = "http://dreamboxupdate.com/"
LICENSE = "CLOSED"
DEPENDS = "fcgi"

SRC_URI_append = ";branch=${BRANCH}"

do_install() {
    oe_runmake install DESTDIR=${D}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = " \
    coreutils-stdbuf \
    haserl \
    lighttpd \
    lighttpd-module-cgi \
    lighttpd-module-fastcgi \
    recovery \
    tpm-ca \
"
