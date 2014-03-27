PRINC = "3"

do_install_append() {
    rm -rf ${D}/${sysconfdir}/network/if-up.d/ntpdate-sync
}