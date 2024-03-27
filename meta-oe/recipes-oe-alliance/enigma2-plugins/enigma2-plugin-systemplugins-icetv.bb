# Gets the package version from the most recent tag in the package repository

SUMMARY = "IceTV EPG & recording management service"
DESCRIPTION = "IceTV provides an online subscription service for EPG and recording management in Australia."
MAINTAINER = "prl <prl@ozemail.com.au>"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://icetv/src/LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"
require conf/python/python3-compileall.inc

DEPENDS += "python3-setuptools-native"

inherit autotools-brokensep gettext gitpkgv ${PYTHON_PN}native ${PYTHON_PN}targetconfig

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://bitbucket.org/prl/icetv.git;protocol=https;branch=master \
    file://0001-update-ax_python_devel.m4.patch \
"

PACKAGES += "${PN}-meta"

S = "${WORKDIR}/git"

FILES:${PN} = "${libdir}/enigma2/python/Plugins/SystemPlugins/IceTV"
FILES:${PN}-meta = "${datadir}/meta"

python populate_packages:prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)

    def getControlLines(mydir, d, package):
        packagename = package[-1]
        import os
        try:
            src = open(mydir + packagename + "/CONTROL/control").read()
        except IOError:
            return
        for line in src.split("\n"):
            full_package = package[0] + '-' + package[1] + '-' + package[2] + '-' + package[3]
            if line.startswith('Depends: '):
                # some plugins still reference twisted-* dependencies, these packages are now called ${PYTHON_PN}-twisted-*
                rdepends = []
                for depend in line[9:].split(','):
                    depend = depend.strip()
                    if depend.startswith('twisted-'):
                        rdepends.append(depend.replace('twisted-', '${PYTHON_PN}-twisted-'))
                    elif depend == 'python-re' or depend == 'python-lang' or depend == 'python-textutils':
                        pass
                    elif depend.startswith('python-'):
                        rdepends.append(depend.replace('python-', '${PYTHON_PN}-'))
                    elif depend.startswith('gst-plugins-'):
                        rdepends.append(depend.replace('gst-plugins-', 'gstreamer1.0-'))
                    elif depend.startswith('enigma2') and not depend.startswith('enigma2-'):
                        pass # Ignore silly depends on enigma2 with all kinds of misspellings
                    else:
                        rdepends.append(depend)
                rdepends = ' '.join(rdepends)
                d.setVar('RDEPENDS:' + full_package, rdepends)
            elif line.startswith('Recommends: '):
                d.setVar('RRECOMMENDS:' + full_package, line[12:])
            elif line.startswith('Description: '):
                d.setVar('DESCRIPTION:' + full_package, line[13:])
            elif line.startswith('Replaces: '):
                d.setVar('RREPLACES:' + full_package, ' '.join(line[10:].split(', ')))
            elif line.startswith('Conflicts: '):
                d.setVar('RCONFLICTS:' + full_package, ' '.join(line[11:].split(', ')))
            elif line.startswith('Maintainer: '):
                d.setVar('MAINTAINER_' + full_package, line[12:])

    mydir = d.getVar('D', True) + "/../git/"
    for package in d.getVar('PACKAGES', d, 1).split():
        getControlLines(mydir, d, package.split('-'))
}
