SECTION = "net"
LICENSE = "GPLv2"

DEPENDS = "cups db openssl"

SRC_URI = "http://ovh.dl.sourceforge.net/sourceforge/netatalk/netatalk-${PV}.tar.gz;name=src \
       file://netatalk-dbd.patch \
       file://netatalk.conf \
       file://AppleVolumes.default \
       file://afpd.conf \
       file://afpd.service \
       file://init"
SRC_URI[src.md5sum] = "f35cd7a4ce26c780de380cd2bcae5ce6"
SRC_URI[src.sha256sum] = "7e02b0d2849ec83459c65d240809ff95ee4a857a3ba38af2cafbbe3717d0c672"

inherit autotools pkgconfig update-rc.d

SSTATE_DUPWHITELIST += "${STAGING_INCDIR}/netatalk/at.h"

INITSCRIPT_NAME = "atalk"
INITSCRIPT_PARAMS = "defaults 20"

PACKAGES = "${PN}-atalkd ${PN}-pap ${PN}-timelord ${PN}-dbg ${PN} ${PN}-doc ${PN}-dev"

RRECOMMENDS_${PN}-atalkd = "kernel-module-appletalk"

FILES_${PN}-atalkd += "${sysconfdir}/netatalk/atalkd.conf \
                       /usr/sbin/atalkd"
FILES_${PN}-pap +=  "/usr/bin/pap \
                     ${sysconfdir}/netatalk/papd.conf \
                     /usr/sbin/papd \
                     /usr/bin/papstatus"
FILES_${PN}-timelord += "/usr/sbin/timelord"
FILES_${PN}-dbg += "${sysconfdir}/netatalk/uams/.debug"

EXTRA_OECONF += "ac_cv_path_KRB5_CONFIG=no \
                 ac_cv_header_rpcsvc_rquota_h=no \
                 --with-bdb=${STAGING_DIR_TARGET}${layout_exec_prefix} \
                 --enable-timelord \
                 --without-shadow \
                 --enable-static=no \
                 --disable-srvloc \
                 --without-pam \
                 --with-ssl-dir=${STAGING_DIR_TARGET}${layout_exec_prefix}"
LDFLAGS += "-lpthread -L${STAGING_LIBDIR}"

do_install_append() {
    install -D -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/atalk
    install -D -m 0644 ${WORKDIR}/netatalk.conf ${D}${sysconfdir}/netatalk/netatalk.conf
    install -D -m 0644 ${WORKDIR}/AppleVolumes.default ${D}${sysconfdir}/netatalk/AppleVolumes.default
    install -D -m 0644 ${WORKDIR}/afpd.conf ${D}${sysconfdir}/netatalk/afpd.conf
    install -D -m 0644 ${WORKDIR}/afpd.service ${D}${sysconfdir}/avahi/services/afpd.service
}
