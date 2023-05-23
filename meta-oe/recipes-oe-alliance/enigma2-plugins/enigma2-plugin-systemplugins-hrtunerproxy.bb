SUMMARY = "Setup Enigma2 to act as HR-Tuner Proxy"
DESCRIPTION = "Setup Enigma2 to act as HR-Tuner Proxy"
MAINTAINER = "OpenViX"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a23a74b3f4caf9616230789d94217acb"
require conf/python/python3-compileall.inc

inherit gitpkgv setuptools3-openplugins gettext

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "${GITPKGVTAG}"
PR = "r1"

SRC_URI = "git://github.com/OpenViX/HRTunerProxy.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

RCONFLICTS:${PN} = "enigma2-plugin-systemplugins-plexdvrapi"
RREPLACES:${PN} = "enigma2-plugin-systemplugins-plexdvrapi"

python populate_packages:prepend() {
    e2_pdir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, e2_pdir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}