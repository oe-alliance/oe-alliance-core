SUMMARY = "multiboot-selector for dreamos"
MAINTAINER = "oe-a"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"
PR = "r0"

SRC_URI = "git://github.com/oe-alliance/MultiBootSelectorPlugin.git;protocol=https;branch=master;subdir=${BP}"

inherit gittag

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_compile() {
}

FILES:${PN} = "${bindir} ${datadir}/multiboot-legacy"

do_install() {
    install -d ${D}/usr/bin/
    install -m 0755 ${S}/${BP}/src/usr/bin/multiboot-selector.sh ${D}/usr/bin/multiboot-selector.sh
    install -d ${D}${datadir}/multiboot-legacy/
    install ${S}/${BP}/src/*.py ${S}/${BP}/src/*.png ${D}${datadir}/multiboot-legacy/
}

INSANE_SKIP = "file-rdeps"
