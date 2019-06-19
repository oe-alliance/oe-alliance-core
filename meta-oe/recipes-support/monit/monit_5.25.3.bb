LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=ea116a7defaf0e93b3bb73b2a34a3f51"

DEPENDS = "openssl libpam"

SRC_URI = "\
        https://mmonit.com/monit/dist/monit-${PV}.tar.gz \
        file://enable-etc-monit.d-include.patch \
        file://init \
        "

SRC_URI[md5sum] = "8d91f6e756cca42450ab0815b3086d5b"
SRC_URI[sha256sum] = "c10258c8839d20864d30390e7cbf2ff5e0480a67a6fb80c02aa457d6e3390569"

inherit autotools-brokensep update-rc.d

INITSCRIPT_NAME = "monit"
INITSCRIPT_PARAMS = "defaults 99"

EXTRA_OECONF = "\
        libmonit_cv_setjmp_available=no \
        libmonit_cv_vsnprintf_c99_conformant=no \
        --with-ssl-lib-dir=${STAGING_LIBDIR} \
        --with-ssl-incl-dir=${STAGING_INCDIR} \
        "

do_install_append() {
        install -d ${D}${sysconfdir}/init.d/
        install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/monit
        sed -i 's:# set daemon  120:set daemon  120:' ${S}/monitrc
        sed -i 's:include /etc/monit.d/:include /${sysconfdir}/monit.d/:' ${S}/monitrc
        install -m 600 ${S}/monitrc ${D}${sysconfdir}/monitrc
        install -m 700 -d ${D}${sysconfdir}/monit.d/
}

CONFFILES_${PN} += "${sysconfdir}/monitrc"
