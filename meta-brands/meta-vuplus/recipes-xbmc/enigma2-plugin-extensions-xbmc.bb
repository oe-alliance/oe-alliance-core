SECTION = "base"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PKG_DATE="20150424.1_beta"

PV="1.1"
PR="${PKG_DATE}_r1"

DEPENDS += "enigma2 virtual/xbmc"
RDEPENDS_${PN} += "virtual/xbmc"

SRC_URI = "http://archive.vuplus.com/download/build_support/e2xbmc-plugin_${PKG_DATE}.tar.gz \
           file://ps.patch \
"

S = "${WORKDIR}/plugin"

PLUGIN_DIR="${D}${libdir}/enigma2/python/Plugins/Extensions/XBMC"

do_install() {
	install -d ${PLUGIN_DIR}
	install -m 0755 ${S}/*.py ${PLUGIN_DIR}
	install -m 0755 ${S}/button.png ${PLUGIN_DIR}
}

do_package_qa() {
}

FILES_${PN}="/"

SRC_URI[md5sum] = "74687180693181e80ab2631741bb95e3"
SRC_URI[sha256sum] = "c9f8f30df576caedf165fe69aeec4acced3904383278fa9278e3923c7fa6a200"

