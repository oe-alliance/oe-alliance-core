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
RUST_PREBUILT_SHA256[x86_64-rustc]  = "77171ba2a0345fdf2abc4fedda55d6de078dae7a68527c28be8c77dcc9604bd5"
RUST_PREBUILT_SHA256[x86_64-cargo]  = "d7674918d28093097614cd9728b6ca60db9ea3038f640f0bd1e9a4188c7568ce"
RUST_PREBUILT_SHA256[x86_64-std]    = "3e58dff2d0b72196b5ea4e90536e174d400de88564a52694686b81e091169933"
RUST_PREBUILT_SHA256[aarch64-rustc] = "89c0f1a3a44df63c95e5d5f2ba093d0a12c5f5f57f8635f8a7a0ce50cf86600e"
RUST_PREBUILT_SHA256[aarch64-cargo] = "113229fcc16cc1ba004923a4cc275fbf3281dafaaf8b97269f9c00189d497fd4"
RUST_PREBUILT_SHA256[aarch64-std]   = "1cf7e2ef58ed1cfa6f1adea18e155cbf254c1951f39e5f5e3376879f55fd64a4"

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
