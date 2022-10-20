SUMMARY = "IceTV EPG & recording management service"
DESCRIPTION = "IceTV provides an online subscription service for EPG and recording management in Australia."
MAINTAINER = "prl"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://IceTV/src/LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"
require conf/python/python3-compileall.inc

inherit autotools-brokensep gettext gitpkgv ${PYTHON_PN}native ${PYTHON_PN}targetconfig

SRCREV = "${AUTOREV}"
PV = "20221016+git${SRCPV}"
PKGV = "20221016+git${GITPKGV}"

SRC_URI = "git://bitbucket.org/prl/icetv.git;protocol=https;branch=master"

PACKAGES += "${PN}-meta"

S = "${WORKDIR}/git"

FILES:${PN} = "${libdir}/enigma2/python/Plugins/SystemPlugins/IceTV"
FILES:${PN}-meta = "${datadir}/meta"

python populate_packages:prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}
