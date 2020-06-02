SUMMARY = "Setup Enigma2 to act as HR-Tuner Proxy"
DESCRIPTION = "Setup Enigma2 to act as HR-Tuner Proxy"
MAINTAINER = "OpenViX"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a23a74b3f4caf9616230789d94217acb"

inherit gitpkgv ${@bb.utils.contains("PYTHON_PN", "python", "distutils-openplugins", "distutils3-openplugins", d)} gettext

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/OpenViX/HRTunerProxy.git;protocol=git"

S = "${WORKDIR}/git"

RCONFLICTS_${PN} = "enigma2-plugin-systemplugins-plexdvrapi"
RREPLACES_${PN} = "enigma2-plugin-systemplugins-plexdvrapi"

RDEPENDS_${PN} = " \
    ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-argparse", "", d)} \
    "

do_install_prepend() {
    echo ${GITPKGVTAG} | awk -F"-" '{print $1}'> ${S}/build/lib/SystemPlugins/HRTunerProxy/PLUGIN_VERSION
}

python populate_packages_prepend() {
    e2_pdir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, e2_pdir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}