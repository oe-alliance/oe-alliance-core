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
RUST_PREBUILT_SHA256[x86_64-rustc]  = "e974f036b28565f37c0f3bd92ddefa809bee16c04f9dcf07b9ed96e05aaaf7c4"
RUST_PREBUILT_SHA256[x86_64-cargo]  = "ea1de9f9e23107d97ee2b41a72c552f34064a593da503789218387aee59f3ba4"
RUST_PREBUILT_SHA256[x86_64-std]    = "fa3ff450172a16c026944030230c5069947af93c728d9179971d44e5e0cfb561"
RUST_PREBUILT_SHA256[aarch64-rustc] = "89fb83041993b48816514815606f53a5264729b8b449671a6b291e6f0ae74f40"
RUST_PREBUILT_SHA256[aarch64-cargo] = "c09425a7f300af148c0bdfded0e7d5f1fe7c075b2b9674e8935afa769297b2f6"
RUST_PREBUILT_SHA256[aarch64-std]   = "9bf796a6ec5b004813ebd0b650775a7c6a4f3aae97ad362ae294798dca4f3b23"

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
