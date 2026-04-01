DESCRIPTION = "E2 HbbTV Plugin"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "vuplus-webkithbbtv-dumpait webkit-hbbtv-browser-${MACHINE} libupnp1.6"

COMPATIBLE_MACHINE = "^(vusolo4k|vuuno4k|vuzero4k|vuuno4kse|vuultimo4k|vuduo4k|vuduo4kse)$"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/WebkitHbbTV.git;protocol=https;branch=main"


do_install:append() {
    install -d ${D}/usr/bin
    install -m 0755 ${S}/WebkitHbbTV/bin/run-webkit.sh ${D}/usr/bin/run-webkit.sh
}

FILES:${PN} += "/usr/bin/run-webkit.sh"
