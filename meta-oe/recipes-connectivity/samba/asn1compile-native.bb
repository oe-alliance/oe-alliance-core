require samba-source.inc

S = "${WORKDIR}/samba-${PV}"

inherit cpan-base perlnative pythonnative native

#DEPENDS += "libxslt-native docbook-xsl-stylesheets-native e2fsprogs readline virtual/libiconv zlib popt 
#DEPENDS += "python-native"

DEPENDS += "e2fsprogs-native zlib-native readline-native"

DEPENDS_append_libc-musl = " libtirpc"
CFLAGS_append_libc-musl = " -I${STAGING_INCDIR}/tirpc"
LDFLAGS_append_libc-musl = " -ltirpc"

EXTRA_OECONF += "--disable-cups \
                 --disable-iprint \
                 --disable-cephfs \
                 --disable-fault-handling \
                 --disable-glusterfs \
                 --disable-rpath \
                 --disable-rpath-install \
                 --disable-rpath-private-install \
                 --enable-fhs \
                 --without-automount \
                 --without-iconv \
                 --without-lttng \
                 --without-ntvfs-fileserver \
                 --without-pam \
                 --without-systemd \
                 --without-utmp \
                 --without-dmapi \
                 --without-fam \
                 --without-gettext \
                 --without-regedit \
                 --without-gpgme \
                 --disable-avahi \
                 --without-quotas \
                 --without-acl-support \
                 --without-winbind \
                 --without-ad-dc \
                 --without-json \
                 --without-libarchive \
                 --disable-python --nopyc --nopyo \
                 --disable-gnutls \
                 --without-dnsupdate \
                 --without-ads \
                 --without-ldap \
                 --nonshared-binary=asn1_compile \
                 --builtin-libraries=replace \
                 --with-static-modules=!DEFAULT,!FORCED \
                 --with-shared-modules=!DEFAULT,!FORCED \
                "

LDFLAGS += "-Wl,-z,relro,-z,now ${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', ' -fuse-ld=bfd ', '', d)}"

# avoids build breaks when using no-static-libs.inc
DISABLE_STATIC = ""

CONFIGUREOPTS = " --prefix=${prefix} \
                  --bindir=${bindir} \
                  --sbindir=${sbindir} \
                  --libexecdir=${libexecdir} \
                  --datadir=${datadir} \
                  --sysconfdir=${sysconfdir} \
                  --sharedstatedir=${sharedstatedir} \
                  --localstatedir=${localstatedir} \
                  --libdir=${libdir} \
                  --includedir=${includedir} \
                  --oldincludedir=${oldincludedir} \
                  --infodir=${infodir} \
                  --mandir=${mandir} \
                  ${PACKAGECONFIG_CONFARGS} \
                "

do_configure() {
    export STAGING_LIBDIR=${STAGING_DIR_HOST}${libdir}
    export STAGING_INCDIR=${STAGING_DIR_HOST}${incdir}
    export PYTHONPATH=${STAGING_DIR_HOST}${PYTHON_SITEPACKAGES_DIR}

    export python_LDFLAGS=""
    export python_LIBDIR=""

    CONFIG_CMD="./configure ${CONFIGUREOPTS} ${EXTRA_OECONF}"
    ${CONFIG_CMD}
}

do_compile () {
    ./buildtools/bin/waf build --targets=asn1_compile
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/bin/default/source4/heimdal_build/asn1_compile ${D}${bindir}
}
