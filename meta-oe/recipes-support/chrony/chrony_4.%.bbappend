FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://0001-add-status-log.patch"

INITSCRIPT_PARAMS = "defaults 12"

do_install:append() {
    sed -i 's/0.openembedded.pool.ntp.org iburst/pool.ntp.org iburst minpoll 11 maxpoll 15/g' ${D}${sysconfdir}/chrony.conf
    sed -i 's/--startas $DAEMON -- "$@"/--startas $DAEMON -- -s/g' ${D}${sysconfdir}/init.d/chronyd
}
