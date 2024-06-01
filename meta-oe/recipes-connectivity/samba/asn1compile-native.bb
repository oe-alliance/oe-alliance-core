require samba-source.inc

S = "${WORKDIR}/samba-${PV}"

inherit pkgconfig cpan-base perlnative python3native native

#DEPENDS += "libxslt-native docbook-xsl-stylesheets-native e2fsprogs readline virtual/libiconv zlib popt"
#DEPENDS += "python3native"

DEPENDS += "zlib-native readline-native gnutls-native libjson-perl-native libparse-yapp-perl-native bison-native icu-native libtasn1-native"

DEPENDS:append:libc-musl = " libtirpc"
CFLAGS:append:libc-musl = " -I${STAGING_INCDIR}/tirpc"
LDFLAGS:append:libc-musl = " -ltirpc"

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
                 --without-ads \
                 --without-ldap \
                 --nonshared-binary=asn1_compile,compile_et \
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
    ./buildtools/bin/waf build --targets=asn1_compile,compile_et
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/bin/default/third_party/heimdal_build/asn1_compile ${D}${bindir}/asn1_compile
    install -m 0755 ${S}/bin/default/third_party/heimdal_build/compile_et ${D}${bindir}/compile_et
}
