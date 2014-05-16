SECTION = "net"
PR = "3"

require conf/license/license-gplv2.inc

DEPENDS = "cups db openssl"

SRC_URI = "${SOURCEFORGE_MIRROR}/project/netatalk/netatalk/${PV}/netatalk-${PV}.tar.gz;name=src \
        file://netatalk.conf \
        file://AppleVolumes.default \
        file://afpd.conf \
        file://afpd.service \
        file://init"

inherit autotools update-rc.d

INITSCRIPT_NAME = "atalk"
INITSCRIPT_PARAMS = "defaults 20"

PACKAGES = "${PN}-atalkd ${PN}-pap ${PN}-timelord ${PN}-dbg ${PN} ${PN}-doc ${PN}-dev"

INSANE_SKIP_${PN} = "dev-so"

RRECOMMENDS_${PN}-atalkd = "kernel-module-appletalk"

FILES_${PN}-atalkd += "${sysconfdir}/netatalk/atalkd.conf \
                    /usr/sbin/atalkd"
FILES_${PN}-pap +=    "/usr/bin/pap \
                    ${sysconfdir}/netatalk/papd.conf \
                    /usr/sbin/papd \
                    /usr/bin/papstatus"
FILES_${PN}-timelord += "/usr/sbin/timelord"
FILES_${PN}-dbg += "${sysconfdir}/netatalk/uams/.debug"
FILES_${PN}-staticdev += "${libdir}/.a"

# FILES_${PN} += "${sysconfdir}/netatalk/uams/uams_clrtxt.so \
#                 ${sysconfdir}/netatalk/uams/uams_dhx2.so \
#                 ${sysconfdir}/netatalk/uams/uams_dhx.so \
#                 "

EXTRA_OECONF += "ac_cv_path_KRB5_CONFIG=no \
                ac_cv_header_rpcsvc_rquota_h=no \
                --with-bdb=${STAGING_DIR_TARGET}${prefix_native} \
                --without-shadow \
                --enable-static=no \
                --disable-srvloc \
                --without-pam \
                --with-ssl-dir=${STAGING_DIR_TARGET}${prefix_native}"
LDFLAGS += "-lpthread -L${STAGING_LIBDIR}"

do_install_append() {
    install -D -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/atalk
    install -D -m 0644 ${WORKDIR}/netatalk.conf ${D}${sysconfdir}/netatalk/netatalk.conf
    install -D -m 0644 ${WORKDIR}/AppleVolumes.default ${D}${sysconfdir}/netatalk/AppleVolumes.default
    install -D -m 0644 ${WORKDIR}/afpd.conf ${D}${sysconfdir}/netatalk/afpd.conf
    install -D -m 0644 ${WORKDIR}/afpd.service ${D}${sysconfdir}/avahi/services/afpd.service
}

SRC_URI[src.md5sum] = "1e49e596f1f119b042559a6cd61039cd"
SRC_URI[src.sha256sum] = "4abbdc2820124c61d831f010d4b17abe1227b94d30b3fad063ae2ae80f6da15a"
