FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PKGV = "${GITPKGVTAG}"

S = "${WORKDIR}/git"


SRC_URI = " \
            git://github.com/atvcaptain/dvb-apps.git \
            git://linuxtv.org/git/dtv-scan-tables.git;protocol=http;destsuffix=dvb-apps/initial-scan-tables;name=scantables \
            file://util-DVBC_ANNEX_AC.patch \
"
