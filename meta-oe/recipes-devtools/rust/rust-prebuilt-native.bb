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

RUST_PREBUILT_SYS = "x86_64-unknown-linux-gnu"
RUST_PREBUILT_BASE = "https://static.rust-lang.org/dist"

SRC_URI[rustc.sha256sum] = "9819d0a32d56bd339585319c80260e332779f5541fd66838ab7e016d6c814819"
SRC_URI[cargo.sha256sum] = "e1be5f5ff7f7f80ca506fb65770b759edbdc6d303781ed71c5de8ec8a8394779"
SRC_URI[std.sha256sum] = "1c1e704ae80126b7de34f72ea2825f7fd01736dec20732faed47374b95282fba"

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

# Upstream binaries: stripped already, and not built by us.
INSANE_SKIP:${PN} += "already-stripped arch staticdev ldflags textrel file-rdeps"
EXCLUDE_FROM_SHLIBS = "1"
