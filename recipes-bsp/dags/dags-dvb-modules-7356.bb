SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20140430"
KV = "3.9.7"
PV = "${KV}+${SRCDATE}"
PR = "r2"

SRC_URI[md5sum] = "0b4774d9e3df496d496fbaaf68fa875a"
SRC_URI[sha256sum] = "86e631f296f3f37ed8eed68812a1422136734f109b86bb5d6e06db7f675eee8b"

SRC_URI = "http://en3.homeftp.net/release/images/oedrivers/bcmlinuxdvb_7356-${KV}-${SRCDATE}.tar.gz"

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
