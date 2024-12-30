DESCIPTION = "helper tool to deliver hotplug events to e2"
MAINTAINER = "oe-alliance"
require conf/license/license-gplv2.inc

RDEPENDS:${PN} = "checkinternet"

inherit gitpkgv

PV = "2.0+git"
PKGV = "2.0+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/oe-alliance/hotplug-e2-helper.git;protocol=https;branch=test"

S = "${WORKDIR}/git"

inherit autotools

pkg_postinst:${PN} () {
    rm -f $D/autofs
    true
}
