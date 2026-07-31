SUMMARY = "Provides web pages for the rescue partition"
HOMEPAGE = "http://dreamboxupdate.com/"
require conf/license/license-close.inc
DEPENDS = "fcgi"

SRC_URI:append = ";branch=${BRANCH}"

do_install() {
    oe_runmake install DESTDIR=${D}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = " \
    coreutils-stdbuf \
    haserl \
    lighttpd \
    lighttpd-module-cgi \
    lighttpd-module-fastcgi \
    recovery \
    tpm-ca \
"
