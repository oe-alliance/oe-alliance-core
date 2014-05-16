SUMMARY = "This is a GPL licensed C++ class library wrapping \
the berkeley sockets C API, and therefore works on most \
unixes and also win32. The library is in use in a number \
of real world applications, both commercial and open source."
LICENSE = "GPLv3"
SECTION = "libs"
DEPENDS = "openssl libxml2"

require conf/license/license-gplv2.inc

SRC_URI = "http://www.alhem.net/Sockets/Sockets-2.3.9.7.tar.gz"

S = "${WORKDIR}/Sockets-2.3.9.7"
FILES_${PN} = "${libdir}/*"

CXXFLAGS_append = " -O2 -Wall -g -MD -D_VERSION='"2.3.9.7"' -O2 -DLINUX -fPIC"
export LDFLAGSSO = " -L${STAGING_LIBDIR} -shared \
                     -Wl,-lssl \
                     -Wl,-lcrypto \
                     -Wl,-lxml2 \
                     -Wl,-lpthread \
                     -Wl,-hlibSocket.so.2 \
                     -Wl,-call_shared"

do_compile() {
    oe_runmake all
    oe_runmake shared
    ${STRIP} libSockets.so.2.3.9.7
}

do_install() {
    oe_libinstall -so libSockets ${STAGING_LIBDIR}
    install -d ${STAGING_INCDIR}/Sockets/
    for f in *.h
    do
        install -m 0644 $f ${STAGING_INCDIR}/Sockets/
    done

    install -d ${D}/${libdir}
    install -m 644 ${STAGING_LIBDIR}/libSockets.so.2.3.9.7 ${D}/${libdir}
    ln -s libSockets.so.2.3.9.7 ${D}/${libdir}/libSockets.so.2
    ln -s libSockets.so.2.3.9.7 ${D}/${libdir}/libSockets.so.2.3
    ln -s libSockets.so.2.3.9.7 ${D}/${libdir}/libSockets.so.2.3.9
    ln -s libSockets.so.2.3.9.7 ${D}/${libdir}/libSocket.so.2
    ln -s libSockets.so.2.3.9.7 ${D}/${libdir}/libSocket.so.2.3
    ln -s libSockets.so.2.3.9.7 ${D}/${libdir}/libSocket.so.2.3.9
    ln -s libSockets.so.2.3.9.7 ${D}/${libdir}/libSocket.so.2.3.9.7
}

SRC_URI[md5sum] = "246d8b801b12d756928ec2672747e610"
SRC_URI[sha256sum] = "dc6411eb53933c0aba639b3ace98b34b674a97aad6b8613f02e5e0aa2af92b24"

