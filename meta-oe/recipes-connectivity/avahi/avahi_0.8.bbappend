inherit upx-compress

AVAHI_GTK = "gtk"

do_install:append () {
        rm -rf ${D}${libdir}/libavahi-ui.so*
        rm -rf ${D}${bindir}/b*
        rm -rf ${D}${datadir}/avahi/interfaces
        rm -rf ${D}${datadir}/applications
        rm -rf ${D}${datadir}/man
}

# for shairplay ...
PACKAGECONFIG += "libdns_sd"
