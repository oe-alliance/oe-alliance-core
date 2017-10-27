SECTION = "net"

require conf/license/license-gplv2.inc

DEPENDS = "cups db openssl"

SRC_URI = "${SOURCEFORGE_MIRROR}/project/netatalk/netatalk/${PV}/netatalk-${PV}.tar.gz;name=src \
        file://afp.conf \
        file://afpd.service"

inherit autotools pkgconfig

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-dev"

INSANE_SKIP_${PN} = "dev-so"

RRECOMMENDS_${PN} = "kernel-module-appletalk"

SSTATE_DUPWHITELIST += "${STAGING_INCDIR}/netatalk/at.h"

EXTRA_OECONF += "ac_cv_path_KRB5_CONFIG=no \
                ac_cv_header_rpcsvc_rquota_h=no \
                --with-bdb=${STAGING_DIR_TARGET}${prefix_native} \
                --with-ssl-dir=${STAGING_DIR_TARGET}${prefix_native} \
                --without-shadow \
                --enable-static=no \
                --disable-srvloc \
                --without-pam \
                --with-init-style=debian"
LDFLAGS += "-lpthread -L${STAGING_LIBDIR}"

do_install_append() {
    install -D -m 0644 ${WORKDIR}/afp.conf ${D}${sysconfdir}/afp.conf
    install -D -m 0644 ${WORKDIR}/afpd.service ${D}${sysconfdir}/avahi/services/afpd.service
}

SRC_URI[src.md5sum] = "0d9446247e1eb260bf4205ea075958c0"
SRC_URI[src.sha256sum] = "ecf46a6d5eb956572e7627f598a6e1f2a2c180051daf05db42b44e3927d89988"
