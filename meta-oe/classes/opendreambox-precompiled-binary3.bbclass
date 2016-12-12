require conf/license/license-close.inc
PRECOMPILED_NAME ?= "${PN}"
PRECOMPILED_ARCH ?= "${PACKAGE_ARCH}"
PRECOMPILED_VERSION ?= "${PV}"
PRECOMPILED_URI ?= "http://dreamboxupdate.com/download/opendreambox/2.5.0/${@precompiledPath(d)};name=${PRECOMPILED_ARCH}"

SRC_URI += "${PRECOMPILED_URI}"

S = "${WORKDIR}/${PRECOMPILED_NAME}_${PRECOMPILED_VERSION}_${PRECOMPILED_ARCH}"

def precompiledPath(d):
    pn = d.getVar('PRECOMPILED_NAME', True)
    pv = d.getVar('PRECOMPILED_VERSION', True)
    package_arch = d.getVar('PRECOMPILED_ARCH', True)
    md5sum = d.getVarFlag('SRC_URI', '%s.md5sum' % package_arch, True)
    return '%s/%s/%s/%s/%s_%s_%s.tar.xz' % (pn, pv, package_arch, md5sum, pn, pv, package_arch)

python () {
    package_arch = d.getVar('PRECOMPILED_ARCH', True)
    varflags = (d.getVarFlags('SRC_URI') or {}).keys()
    if '%s.md5sum' % package_arch not in varflags:
        pn = d.getVar('PRECOMPILED_NAME', True)
        pv = d.getVar('PRECOMPILED_VERSION', True)
        raise bb.parse.SkipPackage("No checksum for %s found in recipe for precompiled binary %s version %s" % (package_arch, pn, pv))
}

do_install() {
    find . -depth -not -path "./patches*" -not -path "./.pc*" -print0 | cpio --null -pdlu ${D}
    chown -hR root:root ${D}
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

INSANE_SKIP_${PN}_append = " already-stripped"
