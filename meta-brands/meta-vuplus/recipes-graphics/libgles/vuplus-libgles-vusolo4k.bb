require vuplus-libgles.inc

SRCDATE = "20151104"
SRCDATE_PR = "r0"
PV="15.2"

do_install() {
        install -d ${D}${libdir}
        install -m 0755 ${S}/lib/*.so ${D}${libdir}
        install -m 0755 ${S}/lib/libliveMedia.so.1.2.1 ${D}${libdir}
        install -m 0755 ${S}/lib/libgroupsock.so.0.0.0 ${D}${libdir}
        install -m 0755 ${S}/lib/libUsageEnvironment.so.0.0.0 ${D}${libdir}
        install -m 0755 ${S}/lib/libBasicUsageEnvironment.so.0.0.1 ${D}${libdir}
        ln -s libv3ddriver.so ${D}${libdir}/libEGL.so
        ln -s libv3ddriver.so ${D}${libdir}/libGLESv2.so

        ln -s libliveMedia.so.1.2.1 ${D}${libdir}/libliveMedia.so.1
        ln -s libgroupsock.so.0.0.0 ${D}${libdir}/libgroupsock.so.0
        ln -s libUsageEnvironment.so.0.0.0 ${D}${libdir}/libUsageEnvironment.so.0
        ln -s libBasicUsageEnvironment.so.0.0.1 ${D}${libdir}/libBasicUsageEnvironment.so.0

        install -d ${D}${includedir}
        cp -a ${S}/include/* ${D}${includedir}/
}


SRC_URI[md5sum] = "7e2d98d0ac0df3c67da843e3d5b05510"
SRC_URI[sha256sum] = "36379a1d6ebeda04a20c6c868c2a93fe8d4e3e30e6bf4fc6b800031bb2718dc2"
