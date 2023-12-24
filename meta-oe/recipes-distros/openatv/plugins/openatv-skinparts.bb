DESCRIPTION = "Skinparts packs"
SUMMARY = "Skinparts"
MAINTAINER = "www.opena.tv Fischreiher"
LICENSE = "proprietary"
PACKAGES = "${PN}-meta ${PN}"
PACKAGES_DYNAMIC = "enigma2-plugin-skincomponents-*"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
inherit allarch

SRCREV = "${AUTOREV}"
PV = "0.0+git"
PKGV = "0.0+git${GITPKGV}"
PR = "r1"

SRC_URI = "git://github.com/openatv/skin-parts.git;protocol=https;branch=master"

# openatv-skinparts is an empty package, dependencies are avoided by do_split_packages( [...] extra_depends='')
ALLOW_EMPTY:${PN} = "1"

FILES:${PN} = "/usr/share/enigma2/skinparts"
FILES:${PN}-meta = "${datadir}/meta"
RDEPENDS:${PN}-meta = ""

inherit autotools-brokensep

S = "${WORKDIR}/git"

python populate_packages:prepend () {
    enigma2_skinpartsdir = bb.data.expand('${datadir}/enigma2/skinparts', d)
    do_split_packages(d, enigma2_skinpartsdir, '(.*?)/.*', 'enigma2-plugin-skincomponents-%s', 'Enigma2 Skinpart: %s', recursive=True, match_path=True, prepend=True, extra_depends='')
}

do_populate_sysroot[noexec] = "1"
