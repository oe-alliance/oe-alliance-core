SUMMARY = "libcurl with browser TLS and HTTP fingerprint impersonation"
HOMEPAGE = "https://github.com/lexiforest/curl-impersonate"
LICENSE = "Apache-2.0 AND BSD-3-Clause AND ISC AND MIT AND Zlib AND curl"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=64fec97be378d46097eeda4e8ac99d46 \
    file://oe-licenses/zlib;md5=b51a40671bc46e961c0498897742c0b8 \
    file://oe-licenses/zstd;md5=0822a32f7acdbe013606746641746ee8 \
    file://oe-licenses/brotli;md5=941ee9cd1609382f946352712a319b4b \
    file://oe-licenses/boringssl;md5=0131a611be3a37729f61e0b26319da57 \
    file://oe-licenses/nghttp2;md5=764abdf30b2eadd37ce47dcbce0ea1ec \
    file://oe-licenses/ngtcp2;md5=de0966c8ff4f62661a3da92967a75434 \
    file://oe-licenses/nghttp3;md5=2005b8c7595329cc8ab211085467600a \
    file://oe-licenses/curl;md5=0515352b285b9c3f66464b135c9c0fdc \
    file://oe-licenses/cares;md5=d3e72a10e08191f2ca1be3f3228d78f3 \
"

DEPENDS = "go-native perl-native patch-native python3-native"
RDEPENDS:${PN} = "bash"
RRECOMMENDS:${PN} = "ca-certificates"

SRC_URI = " \
    git://github.com/lexiforest/curl-impersonate.git;protocol=https;nobranch=1;name=impersonate;destsuffix=impersonate \
    https://github.com/madler/zlib/releases/download/v1.3.1/zlib-1.3.1.tar.gz;name=zlib;unpack=0 \
    https://github.com/facebook/zstd/releases/download/v1.5.7/zstd-1.5.7.tar.gz;name=zstd;unpack=0 \
    git://github.com/google/brotli.git;protocol=https;nobranch=1;name=brotli;destsuffix=sources/brotli \
    git://github.com/google/boringssl.git;protocol=https;nobranch=1;name=boringssl;destsuffix=sources/boringssl \
    https://github.com/nghttp2/nghttp2/releases/download/v1.63.0/nghttp2-1.63.0.tar.bz2;name=nghttp2;unpack=0 \
    https://github.com/ngtcp2/ngtcp2/releases/download/v1.20.0/ngtcp2-1.20.0.tar.bz2;name=ngtcp2;unpack=0 \
    https://github.com/ngtcp2/nghttp3/releases/download/v1.15.0/nghttp3-1.15.0.tar.bz2;name=nghttp3;unpack=0 \
    https://github.com/c-ares/c-ares/releases/download/v1.34.8/c-ares-1.34.8.tar.gz;name=cares;unpack=0 \
    git://github.com/curl/curl.git;protocol=https;nobranch=1;name=curl;destsuffix=sources/curl \
"
SRCREV_impersonate = "6e8f87760a4dd96771e96fc9d55440dcd8845243"
SRC_URI[zlib.sha256sum] = "9a93b2b7dfdac77ceba5a558a580e74667dd6fede4585b91eefb60f03b72df23"
SRC_URI[zstd.sha256sum] = "eb33e51f49a15e023950cd7825ca74a4a2b43db8354825ac24fc1b7ee09e6fa3"
SRCREV_brotli = "028fb5a23661f123017c060daa546b55cf4bde29"
SRCREV_boringssl = "156c7b75ae9b8c3b3f847acf264f17594c3859fb"
SRC_URI[nghttp2.sha256sum] = "607b174554d22a828bc532d1d734fe0f729b5d5ed207f2f12e96a62e83f29c55"
SRC_URI[ngtcp2.sha256sum] = "871ec97ad86803cf312901b0c393b0ee70163e25a87c9b2894d1234341ce4e97"
SRC_URI[nghttp3.sha256sum] = "c6c491a52804814098e446630e6efc459afc0d3da7952ffe6cbdc0b3f99b2b62"
SRC_URI[cares.sha256sum] = "c222b6d681096f9444d2c4863d2c1174019e27cacca0a4a5c114d36dd7d7bf78"
SRCREV_curl = "01346829096c61b372692f6dc43ffa778c6caccd"

SRCREV_FORMAT = "impersonate_brotli_boringssl_curl"
S = "${UNPACKDIR}/impersonate"

inherit cmake python3native upx-compress

export GOTOOLCHAIN = "local"
export GOPROXY = "off"
export GOSUMDB = "off"

# Capture bundled dependency notices before do_populate_lic. ExternalProject
# unpacks these archives later, during compile, without network access.
python do_unpack:append() {
    from pathlib import Path
    import tarfile
    import zipfile
    sources = {
        'zlib': ('zlib-1.3.1.tar.gz', 'LICENSE'),
        'zstd': ('zstd-1.5.7.tar.gz', 'LICENSE'),
        'brotli': ('sources/brotli', 'LICENSE'),
        'boringssl': ('sources/boringssl', 'LICENSE'),
        'nghttp2': ('nghttp2-1.63.0.tar.bz2', 'COPYING'),
        'ngtcp2': ('ngtcp2-1.20.0.tar.bz2', 'COPYING'),
        'nghttp3': ('nghttp3-1.15.0.tar.bz2', 'COPYING'),
        'cares': ('c-ares-1.34.8.tar.gz', 'LICENSE.md'),
        'curl': ('sources/curl', 'COPYING'),
    }
    destination = Path(d.getVar('S')) / 'oe-licenses'
    destination.mkdir(parents=True, exist_ok=True)
    for name, (filename, notice) in sources.items():
        archive = Path(d.getVar('UNPACKDIR')) / filename
        if archive.is_dir():
            content = (archive / notice).read_bytes()
        elif filename.endswith('.zip'):
            with zipfile.ZipFile(archive) as source:
                member = min((n for n in source.namelist() if n.endswith('/' + notice)), key=len)
                content = source.read(member)
        else:
            with tarfile.open(archive) as source:
                member = min((n for n in source.getnames() if n.endswith('/' + notice)), key=len)
                content = source.extractfile(member).read()
        (destination / name).write_bytes(content)
}

# BoringSSL target.h recognizes little-endian MIPS. Use its portable C
# implementation there; big-endian MIPS is not supported by this source.
COMPATIBLE_HOST = "(arm|aarch64|i.86|x86_64|riscv64|mipsel|mips64el).*-linux.*"

EXTRA_OECMAKE += " \
    -DCURL_IMPERSONATE_VERSION=${PV} \
    -DUSE_LIBIDN2=OFF \
    -DSUBJOBS=${@oe.utils.parallel_make(d, False)} \
    -DCURL_CA_BUNDLE=${sysconfdir}/ssl/certs/ca-certificates.crt \
    -DCURL_CA_PATH=${sysconfdir}/ssl/certs \
"
EXTRA_OECMAKE:append:mipsel = " -DDISABLE_BORINGSSL_ASM=ON"
EXTRA_OECMAKE:append:mips64el = " -DDISABLE_BORINGSSL_ASM=ON"

python do_prepare_impersonate_build() {
    """Adapt the upstream superbuild to BitBake-fetched sources and OE toolchains."""
    from pathlib import Path
    
    cmake_file = str(Path(d.getVar("S")) / "CMakeLists.txt")
    unpack_dir = d.getVar("UNPACKDIR")
    libdir = d.getVar("libdir")
    path = Path(cmake_file)
    text = path.read_text(encoding="utf-8")
    marker = "# OE offline superbuild"
    if marker in text:
        return
    
    archives = {
        "ZLIB": "zlib-1.3.1.tar.gz",
        "ZSTD": "zstd-1.5.7.tar.gz",
        "NGHTTP2": "nghttp2-1.63.0.tar.bz2",
        "NGTCP2": "ngtcp2-1.20.0.tar.bz2",
        "NGHTTP3": "nghttp3-1.15.0.tar.bz2",
        "CARES": "c-ares-1.34.8.tar.gz",
    }
    overrides = [marker]
    for name, archive in archives.items():
        local = Path(unpack_dir) / archive
        if not local.is_file():
            raise SystemExit(f"BitBake-fetched source missing: {local}")
        overrides.append(f'set({name}_URL "{local.as_posix()}")')
    # ExternalProject copies pinned Git checkouts before applying its patches.
    import re
    for project in ('brotli', 'boringssl', 'curl'):
        local = Path(unpack_dir) / 'sources' / project
        if not local.is_dir():
            bb.fatal('Missing BitBake Git source: ' + str(local))
        pattern = r'(ExternalProject_Add\(' + project + r'\s+(?:LIST_SEPARATOR \|\s+)?)URL "[^"\n]+"\s+URL_HASH "[^"\n]+"'
        command = 'DOWNLOAD_COMMAND @OE_DOLLAR@{CMAKE_COMMAND} -E copy_directory "' + local.as_posix() + '" <SOURCE_DIR>'
        text, count = re.subn(pattern, lambda m: m.group(1) + command, text)
        if count != 1:
            bb.fatal('Unexpected ExternalProject download layout: ' + project)
    needle = 'set(_disable_boringssl_asm_default OFF)'
    if text.count(needle) != 1:
        raise SystemExit("Unexpected upstream source URL layout")
    text = text.replace(needle, "\n".join(overrides) + "\n\n" + needle)
    
    needle = 'set(_toolchain_cmake_args)'
    if text.count(needle) != 1:
        raise SystemExit("Unexpected upstream toolchain layout")
    text = text.replace(needle, '''set(_toolchain_cmake_args
      "-DCMAKE_TOOLCHAIN_FILE=@OE_DOLLAR@{CMAKE_TOOLCHAIN_FILE}"
      "-DCMAKE_C_FLAGS=@OE_DOLLAR@{CMAKE_C_FLAGS}"
      "-DCMAKE_CXX_FLAGS=@OE_DOLLAR@{CMAKE_CXX_FLAGS}"
      "-DCMAKE_EXE_LINKER_FLAGS=@OE_DOLLAR@{CMAKE_EXE_LINKER_FLAGS}"
      "-DCMAKE_SHARED_LINKER_FLAGS=@OE_DOLLAR@{CMAKE_SHARED_LINKER_FLAGS}"
      -DCMAKE_SKIP_RPATH=ON
    )''')
    # Only the outer install layout changes; private static dependencies use lib.
    text = text.replace(
        '-DCMAKE_INSTALL_PREFIX=' + chr(36) + '{CMAKE_INSTALL_PREFIX}\n      -DCMAKE_INSTALL_LIBDIR=lib',
        '-DCMAKE_INSTALL_PREFIX=' + chr(36) + '{CMAKE_INSTALL_PREFIX}\n      -DCMAKE_INSTALL_LIBDIR=' + libdir.removeprefix('/usr/'),
    )
    text = text.replace("@OE_DOLLAR@", chr(36))
    path.write_text(text, encoding="utf-8")
    
    toolchain = Path(d.getVar("WORKDIR")) / "toolchain.cmake"
    text = toolchain.read_text(encoding="utf-8")
    if "curl-impersonate private dependencies" not in text:
        text += "\n# curl-impersonate private dependencies\nlist(PREPEND CMAKE_FIND_ROOT_PATH \"" + d.getVar("B") + "/deps/install\")\n"
        toolchain.write_text(text, encoding="utf-8")
}
addtask prepare_impersonate_build after do_generate_toolchain_file do_prepare_recipe_sysroot before do_configure

do_install:append() {
    # The profile launchers use Bash; avoid a host-style /usr/bin/env shebang.
    sed -i '1s|^#!/usr/bin/env bash$|#!${base_bindir}/bash|' ${D}${bindir}/curl_*
    # The superbuild places license notices at the prefix root.
    install -d ${D}${datadir}/licenses/${PN}
    for notice in ${D}${prefix}/LICENSE*; do
        [ ! -f "$notice" ] || mv "$notice" ${D}${datadir}/licenses/${PN}/
    done
    # Avoid installing curl headers over the system curl development package.
    install -d ${D}${includedir}/curl-impersonate
    mv ${D}${includedir}/curl ${D}${includedir}/curl-impersonate/
}

FILES:${PN} += "${datadir}/licenses/${PN}"
