PR = "2"

DEPENDS += "wolfssl"

PACKAGECONFIG[gnutls] =  "--without-gnutls"

EXTRA_OECONF = "--with-zlib=${STAGING_LIBDIR}/../ \
                --without-libssh2 \
                --with-random=/dev/urandom \
                --without-libidn \
                --enable-crypto-auth \
                --disable-ldap \
                --disable-ldaps \
                --with-ca-bundle=${sysconfdir}/ssl/certs/ca-certificates.crt \
                --with-wolfssl \
                --without-gnutls \
                "
