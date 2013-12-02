DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20131122"
KV = "3.10.10"
PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI = "http://archiv.mixos-support.com/ebox-dvb-modules-${MACHINE}-${KV}-${SRCDATE}.zip"


S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    for f in *.ko; do
        install -m 0644 ${WORKDIR}/$f ${D}/lib/modules/${KV}/extra/$f;
    done
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in `ls | grep \\.ko | sed -e 's/.ko//g'`; do
    echo $i >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"

SRC_URI[md5sum] = "f9c6ff656875267667f45c8998454423"
SRC_URI[sha256sum] = "ca6c6c54c3c6c2d5ea98bed3b26a7e921c7e0730f59a67053185a53718e34d6b"
