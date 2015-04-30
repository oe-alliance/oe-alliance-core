SRC_URI += "file://config-lfs.patch \
            file://quota.patch;striplevel=0 \
            "

EXTRA_OECONF += "\
        --without-ads \
        --without-winbind \
        --without-ldap \
        --without-krb5"

PACKAGES =+ "libnetapi libtdb libsmbsharemodes libsmbclient libsmbclient-dev swat"

FILES_libnetapi = "${libdir}/libnetapi.so.*"
FILES_libsmbsharemodes = "${libdir}/libsmbsharemodes.so.*"
FILES_libtdb = "${libdir}/libtdb.so.*"
FILES_libsmbclient = "${libdir}/libsmbclient.so.*"
FILES_libsmbclient-dev = "${libdir}/libsmbclient.so ${includedir}"
FILES_swat       = "${sbindir}/swat ${datadir}/swat ${libdir}/*.msg"
