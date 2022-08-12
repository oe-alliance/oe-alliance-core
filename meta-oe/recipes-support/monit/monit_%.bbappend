FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "file://enable-etc-monit.d-include.patch \
                file://init"

do_install:append() {
        install -d ${D}${sysconfdir}/init.d/
        install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/monit
        sed -i 's:# set daemon  120:set daemon  120:' ${S}/monitrc
        sed -i 's:include /etc/monit.d/:include /${sysconfdir}/monit.d/:' ${S}/monitrc
        install -m 600 ${S}/monitrc ${D}${sysconfdir}/monitrc
        install -m 700 -d ${D}${sysconfdir}/monit.d/
}

CONFFILES:${PN} += "${sysconfdir}/monitrc"
