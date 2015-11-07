SECTION = "base"
LICENSE = "CLOSED"
require conf/license/license-close.inc
SRCDATE = "20150506.0_beta"

PV = "1.0"
PR = "${SRCDATE}_r0"

DEPENDS += "enigma2 virtual/xbmc"
RDEPENDS_${PN} += "virtual/xbmc"

SRC_URI = "http://archive.vuplus.com/download/build_support/e2xbmc-plugin_${SRCDATE}.tar.gz"

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

SRC_URI[md5sum] = "f619ffc2e56f7aac7721115cb02c1e51"
SRC_URI[sha256sum] = "1f996832ece11f40b264a1d05f4e4e0c86b16a0cb23d24a960bcc572924043d2"

