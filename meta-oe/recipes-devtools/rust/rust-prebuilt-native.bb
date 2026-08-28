SUMMARY = "Upstream prebuilt Rust toolchain components for the build host"
DESCRIPTION = "Fetches rustc, cargo and the host standard library from \
static.rust-lang.org and stages them so rust-native can install them instead \
of bootstrapping a compiler from source. Only the component trees are staged; \
rust-native remains the recipe that provides rustc and cargo."
HOMEPAGE = "https://www.rust-lang.org"
LICENSE = "Apache-2.0 OR MIT"
LIC_FILES_CHKSUM = "file://LICENSE-APACHE;md5=71b224ca933f0676e26d5c2e2271331c"

# Track whatever rust version oe-core carries, so a bump there needs no change here.
def rust_recipe_version(d):
    import glob, os, re
    core = d.getVar('COREBASE') or ''
    best = ''
    pattern = os.path.join(core, 'meta', 'recipes-devtools', 'rust', 'rust_*.bb')
    for f in glob.glob(pattern):
        m = re.match(r'rust_(\d[\d.]*)\.bb$', os.path.basename(f))
        if m:
            cur = [int(x) for x in m.group(1).split('.')]
            if not best or cur > [int(x) for x in best.split('.')]:
                best = m.group(1)
    return best

PV = "${@rust_recipe_version(d)}"

RUST_PREBUILT_SYS = "${BUILD_ARCH}-unknown-linux-gnu"
RUST_PREBUILT_BASE = "https://static.rust-lang.org/dist"

# sha256 of the upstream dist tarballs, per build host arch
RUST_PREBUILT_SHA256[x86_64-rustc]  = "0e37cb339f447fc44d6d781073bacacebfdc5612f2600e4c7e84c266f5f3aced"
RUST_PREBUILT_SHA256[x86_64-cargo]  = "2f512d170d3dd23e16ababcda32ee2e6d5172d861a7af1f504e0b1e270cafab9"
RUST_PREBUILT_SHA256[x86_64-std]    = "f5022e6c95a5ad23cca2513dc8281200f585fa188de6370aa37b128a43f876a3"
RUST_PREBUILT_SHA256[aarch64-rustc] = "00590657f2356d7163ca5ef295283523974c340fa21bb94b420ce794f29b358c"
RUST_PREBUILT_SHA256[aarch64-cargo] = "5784379d73ac881d15a9e67eed2882cd58c747c276221c23cdf9aff37e015ff6"
RUST_PREBUILT_SHA256[aarch64-std]   = "a36f7ac98af20ef0ba6368aace0345efabbebaef7962eba99f47190fd256162d"

SRC_URI[rustc.sha256sum] = "${@d.getVarFlag('RUST_PREBUILT_SHA256', d.getVar('BUILD_ARCH') + '-rustc') or ''}"
SRC_URI[cargo.sha256sum] = "${@d.getVarFlag('RUST_PREBUILT_SHA256', d.getVar('BUILD_ARCH') + '-cargo') or ''}"
SRC_URI[std.sha256sum] = "${@d.getVarFlag('RUST_PREBUILT_SHA256', d.getVar('BUILD_ARCH') + '-std') or ''}"

SRC_URI = "\
    ${RUST_PREBUILT_BASE}/rustc-${PV}-${RUST_PREBUILT_SYS}.tar.xz;name=rustc \
    ${RUST_PREBUILT_BASE}/cargo-${PV}-${RUST_PREBUILT_SYS}.tar.xz;name=cargo \
    ${RUST_PREBUILT_BASE}/rust-std-${PV}-${RUST_PREBUILT_SYS}.tar.xz;name=std \
"

S = "${UNPACKDIR}/cargo-${PV}-${RUST_PREBUILT_SYS}"

RUST_PREBUILT_STAGE = "${datadir}/rust-prebuilt"

inherit native

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    install -d ${D}${RUST_PREBUILT_STAGE}
    for comp in ${UNPACKDIR}/*-${PV}-${RUST_PREBUILT_SYS}; do
        [ -f "$comp/install.sh" ] || continue
        cp -a "$comp" ${D}${RUST_PREBUILT_STAGE}/
    done
}

SYSROOT_DIRS += "${RUST_PREBUILT_STAGE}"

INHIBIT_SYSROOT_STRIP = "1"

# Upstream binaries: stripped already, and not built by us.
INSANE_SKIP:${PN} += "already-stripped arch staticdev ldflags textrel file-rdeps"
EXCLUDE_FROM_SHLIBS = "1"
