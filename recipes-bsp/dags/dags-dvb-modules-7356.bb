SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20140114"
KV = "3.9.7"
PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI[md5sum] = "a45cea0cc3025bdc053717d49ac6d8dc"
SRC_URI[sha256sum] = "7a88f8b00ff71e2b6836f24bb8d6552b99d4c27ddcd237ff20a4072f9fe73eb8"

SRC_URI = "http://openembedded.homelinux.com/release/images/oedrivers/bcmlinuxdvb_7356-${KV}-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    for f in lib/modules/${KV}/extra/*.ko; do
        install -m 0644 $f ${D}/$f;
    done
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in `ls ${D}/lib/modules/${KV}/extra | grep \\.ko | sed -e 's/.ko//g'`; do
        echo $i _hwtype=\$hwtypenum >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"