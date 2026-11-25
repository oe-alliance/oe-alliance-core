SUMMARY = "dmxcsa - in-kernel CSA-ALT descrambler"
DESCRIPTION = "The dmxcsa kernel module for the box, taken prebuilt from the \
dmxcsa-modules release, and the init script that loads it at boot."
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-or-later;md5=fed54355545ffd980b814dab4a3b312c"

inherit github-releases module-base kernel-module-split update-rc.d

# one module per box, also where boxes share a machine
PACKAGE_ARCH = "${MACHINEBUILD}"

GITHUB_BASE_URI = "https://github.com/oe-alliance-kernels/dmxcsa-modules/releases/"
DMXCSA_MODULE_URI ?= "${GITHUB_BASE_URI}download/v${PV}"

SRC_URI = "file://dmxcsa.init"

S = "${UNPACKDIR}"

# The release names the module dmxcsa-<box>-<kernel version>.ko. The kernel
# version is only known with the kernel sources in place, so the module joins
# SRC_URI when it is fetched and unpacked. The name is the same in every
# release, so the downloaded file carries the version.
def dmxcsa_module_src(d):
    import subprocess
    kv = subprocess.check_output(["make", "-s", "-C", d.getVar("STAGING_KERNEL_DIR"), "kernelversion"], text=True).strip()
    ko = "%s-%s.ko" % (d.getVar("MACHINEBUILD"), kv)
    return " %s/dmxcsa-%s;downloadfilename=dmxcsa-%s-%s" % (d.getVar("DMXCSA_MODULE_URI"), ko, d.getVar("PV"), ko)

do_fetch[depends] += "virtual/kernel:do_shared_workdir"
do_unpack[depends] += "virtual/kernel:do_shared_workdir"

# one module per box and release: the recipe keeps no checksums for them
BB_STRICT_CHECKSUM = "ignore"

# A release may lack the module of a box, a new one or one with a new kernel.
# That must not break the build of the box: warn and leave the packages empty.
python do_fetch:prepend() {
    uri = dmxcsa_module_src(d)
    fetcher = bb.fetch2.Fetch([uri], d)
    try:
        if not os.path.exists(fetcher.localpath(uri)):
            fetcher.checkstatus()
        d.appendVar("SRC_URI", uri)
    except bb.fetch2.FetchError:
        bb.warn("dmxcsa: no module for this box and kernel in the release (%s), the packages stay empty" % uri.split(";")[0].strip())
}

python do_unpack:prepend() {
    uri = dmxcsa_module_src(d)
    if os.path.exists(bb.fetch2.Fetch([uri], d).localpath(uri)):
        d.appendVar("SRC_URI", uri)
}

# Packages with the module are cached as usual. Empty ones are not, so the next
# build looks into the release again. Some of these tasks run before do_fetch,
# with no kernel sources yet, so look for the unpacked module, not the version.
python dmxcsa_cache_only_with_module() {
    import glob
    if not glob.glob(d.expand("${UNPACKDIR}/dmxcsa-${PV}-${MACHINEBUILD}-*.ko")):
        d.setVar("SSTATE_SKIP_CREATION", "1")
}

python () {
    for task in (d.getVar("SSTATETASKS") or "").split():
        d.appendVarFlag(task, "prefuncs", " dmxcsa_cache_only_with_module")
}

# nor are the stamps of an empty build kept
python dmxcsa_forget_empty() {
    import glob
    if d.getVar("SSTATE_SKIP_CREATION") == "1":
        for f in glob.glob(d.getVar("STAMP") + ".do_*"):
            if "sigdata" not in f:
                os.remove(f)
}
do_package_write_ipk[postfuncs] += "dmxcsa_forget_empty"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
	ko=$(ls ${UNPACKDIR}/dmxcsa-${PV}-${MACHINEBUILD}-*.ko 2>/dev/null || true)
	if [ -n "$ko" ]; then
		install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra
		install -m 0644 $ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/dmxcsa.ko
		install -d ${D}${sysconfdir}/init.d
		install -m 0755 ${UNPACKDIR}/dmxcsa.init ${D}${sysconfdir}/init.d/dmxcsa
	fi
}

# the module goes into kernel-module-dmxcsa-<kernel version>, the init script
# into ${PN}, which pulls the module in
KERNEL_MODULES_META_PACKAGE = "${PN}"

# takes over from the installer package of the release
RREPLACES:kernel-module-dmxcsa-${KERNEL_VERSION} = "kernel-module-dmxcsa"
RCONFLICTS:kernel-module-dmxcsa-${KERNEL_VERSION} = "kernel-module-dmxcsa"

INITSCRIPT_NAME = "dmxcsa"
INITSCRIPT_PARAMS = "start 66 S ."

# without a module there is no init script to register either
ALLOW_EMPTY:${PN} = "1"
python populate_packages_updatercd:prepend() {
    if not os.path.exists(d.expand("${PKGD}${sysconfdir}/init.d/dmxcsa")):
        d.setVar("INHIBIT_UPDATERCD_BBCLASS", "1")
}

# the release module is stripped already
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped"
