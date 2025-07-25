SUMMARY = "multiboot-selector for dreamos"
MAINTAINER = "oe-a"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r3"
SRCREV = "${AUTOREV}"

SRC_URI = " \
        git://github.com/oe-alliance/MultiBootSelectorPlugin.git;protocol=https;branch=master;subdir=${BP} \
        file://multiboot-selector.sh \
"

inherit gitpkgv

S = "${UNPACKDIR}"

do_compile() {
}

FILES:${PN} = "${bindir} ${datadir}/multiboot-legacy"

do_install() {
    install -d ${D}/usr/bin/
    install -m 0755 ${S}/multiboot-selector.sh ${D}/usr/bin/multiboot-selector.sh
    install -d ${D}${datadir}/multiboot-legacy/
    install ${S}/${BP}/src/*.py ${S}/${BP}/src/*.png ${D}${datadir}/multiboot-legacy/
}

INSANE_SKIP = "file-rdeps"
