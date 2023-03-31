do_install:append() {
    sed -i 's/0.openembedded.pool.ntp.org/pool.ntp.org/g' ${D}${sysconfdir}/chrony.conf
}
