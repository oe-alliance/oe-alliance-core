inherit upx-compress

ANY_OF_DISTRO_FEATURES += "${GTK2DISTROFEATURES}"

AVAHI_GTK = "gtk"

do_install_append () {
        rm -rf ${D}${libdir}/libavahi-ui.so*
        rm -rf ${D}${bindir}/b*
        rm -rf ${D}${datadir}/avahi/interfaces
        rm -rf ${D}${datadir}/applications
        rm -rf ${D}${datadir}/man
}


# for shairplay ...
PACKAGECONFIG += "libdns_sd"
