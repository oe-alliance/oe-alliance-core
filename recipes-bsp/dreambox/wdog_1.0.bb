SUMMARY = "Dreambox watchdog to automatically restart binaries"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
RDEPENDS_${PN} = "dreambox-compat"
PR = "r3"

SRC_URI[mips32el.md5sum] = "037fcab221a559ec765ce58943c4e95c"
SRC_URI[mips32el.sha256sum] = "96f3bcb25599562c57f3e451cfc884e39b924b6998032b0b562c66430d271483"
SRC_URI[mips32el-nf.md5sum] = "628c9db98032e6bbc942ab5fc94a39db"
SRC_URI[mips32el-nf.sha256sum] = "83b8b3e61c3695e527f42e446628e4b9a8d23dcfe8a1949bfe07315706fc7078"

SRC_URI = "http://dreamboxupdate.com/download/opendreambox/2.0.0/${PN}/${PN}_${PV}_${PACKAGE_ARCH}.tar.bz2;name=${PACKAGE_ARCH}"

S = "${WORKDIR}/${PN}_${PV}_${PACKAGE_ARCH}"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
        install -d ${D}${bindir}
        install -m 0755 wdog ${D}${bindir}
}
