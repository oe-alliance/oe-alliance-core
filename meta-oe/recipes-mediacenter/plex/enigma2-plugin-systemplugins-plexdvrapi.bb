SUMMARY = "Setup Enigma2 for link with Plex DVR API"
DESCRIPTION = "Setup Enigma2 for link with Plex DVR API"
MAINTAINER = "OpenViX"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a23a74b3f4caf9616230789d94217acb"

inherit gitpkgv distutils-openplugins

def get_version():
    from urllib.request import urlopen
    f = urlopen('https://raw.githubusercontent.com/OpenViX/PlexDVRAPI/master/plugin/about.py')
    for line in f.readlines():
        line = line.decode("utf-8").replace('\n','')
        if line.startswith('PLUGIN_VERSION'):
            return line.replace("'","").split(' = ')[1]

SRCREV = "${AUTOREV}"
PKVER = "${@get_version()}"
PV = "${PKVER}+git${SRCPV}"
PKGV = "${PKVER}+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/OpenViX/PlexDVRAPI.git;protocol=git"

S = "${WORKDIR}/git"

RDEPENDS_${PN} = " \
    python-argparse \
    "

python populate_packages_prepend() {
    e2_pdir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, e2_pdir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, e2_pdir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}