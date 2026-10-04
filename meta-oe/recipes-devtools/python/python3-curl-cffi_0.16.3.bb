SUMMARY = "Python libcurl bindings with browser impersonation support"
HOMEPAGE = "https://github.com/lexiforest/curl_cffi"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fd472d9f299a79040ca2acc5d42a1e38"

# Requires a provider of libcurl-impersonate.so, not the ordinary curl library.
DEPENDS = "curl-impersonate python3-cffi-native python3-wheel-native"

RDEPENDS:${PN} = " \
    python3-asyncio \
    python3-certifi \
    python3-cffi \
    python3-compression \
    python3-email \
    python3-json \
    python3-logging \
    python3-netclient \
    python3-numbers \
    python3-threading \
"

PYPI_PACKAGE = "curl_cffi"
COMPATIBLE_HOST = "(arm|aarch64|i.86|x86_64|riscv64|mipsel|mips64el).*-linux.*"
SRC_URI[sha256sum] = "d15d0c2a35f2d75bec430c28946c2a833f421c85773bdb0795182cc5c515665b"

inherit pypi python_setuptools_build_meta

# Link the target library from the recipe sysroot. Do not detect the build
# host, download binary libraries during compilation, or embed a sysroot RPATH.
export CURL_CFFI_LIBDIR = "${STAGING_LIBDIR}"

python do_prepare_cffi_build() {
    from pathlib import Path
    library = Path(d.getVar("STAGING_LIBDIR")) / "libcurl-impersonate.so"
    if not library.exists():
        bb.fatal("curl_cffi requires libcurl-impersonate.so in the target sysroot")
    builder = '"""CFFI builder for OE cross compilation using staged curl-impersonate."""\nimport os\nfrom pathlib import Path\n\nfrom cffi import FFI\n\nroot = Path(__file__).resolve().parent.parent\nffibuilder = FFI()\nffibuilder.set_source(\n    "curl_cffi._wrapper",\n    \'#include "shim.h"\',\n    # Use the version-matched impersonation headers shipped in the sdist.\n    include_dirs=[str(root / "include"), str(root / "ffi")],\n    library_dirs=[os.environ["CURL_CFFI_LIBDIR"]],\n    libraries=["curl-impersonate"],\n    sources=[str(root / "ffi/shim.c")],\n)\nffibuilder.cdef((root / "ffi/cdef.c").read_text(encoding="utf-8"))\n'
    (Path(d.getVar("S")) / "scripts/build.py").write_text(builder, encoding="utf-8")
}
addtask prepare_cffi_build after do_patch do_prepare_recipe_sysroot before do_configure

# The optional command-line frontend imports rich.
PACKAGES =+ "${PN}-cli"
FILES:${PN}-cli = "${bindir}/curl-cffi ${PYTHON_SITEPACKAGES_DIR}/curl_cffi/cli"
RDEPENDS:${PN}-cli = "${PN} python3-rich"

include python3-package-split.inc

# curl_cffi reads its version via importlib.metadata on import
FILES:${PN}-doc:remove = "${PYTHON_SITEPACKAGES_DIR}/*-info"
FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/curl_cffi-${PV}.dist-info"
