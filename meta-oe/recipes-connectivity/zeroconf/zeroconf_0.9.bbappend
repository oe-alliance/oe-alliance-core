do_install_append() {
        sed -e 's,^#FALLBACK=yes,FALLBACK=yes,' -i ${D}${sysconfdir}/default/zeroconf
}
