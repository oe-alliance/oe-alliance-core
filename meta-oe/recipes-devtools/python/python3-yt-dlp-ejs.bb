SUMMARY = "JavaScript challenge solver for yt-dlp"
DESCRIPTION = "YouTube guards its stream URLs with a value that has to be \
recomputed by a function from the player javascript. yt-dlp runs that function in \
a javascript runtime and takes the script from here. Its own source tree carries \
the script only in the deno and bun flavours, which pull their dependencies \
through NPM imports, so node and quickjs have nothing to run without this package \
and fall back to downloading the script at runtime."
HOMEPAGE = "https://github.com/yt-dlp/ejs"
LICENSE = "ISC AND MIT AND Unlicense"
LIC_FILES_CHKSUM = "file://yt_dlp_ejs-${PV}.dist-info/licenses/LICENSE;md5=f4c62131f879a8445e16a7f265aea635"

require recipes-devtools/python/python3-yt-dlp-ejs.inc

PV = "${YT_DLP_EJS_PV}"

inherit pypi python3-dir allarch

PYPI_PACKAGE = "yt_dlp_ejs"

# The source distribution ships typescript and bundles it with pnpm, deno, bun or
# npm, all of which want the network during the build. Take the wheel instead, it
# holds the two scripts ready to use. The predictable URL of the class names a
# source archive, and a wheel is a zip, so both have to be spelled out here.
PYPI_SRC_URI = "https://files.pythonhosted.org/packages/py3/y/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}-py3-none-any.whl;downloadfilename=${BPN}-${PV}.zip;subdir=${PYPI_PACKAGE}-${PV}"
SRC_URI[sha256sum] = "${YT_DLP_EJS_SHA256}"

# yt-dlp imports the package and reads its version attribute, it never asks the
# installed metadata, so the dist-info directory stays out of the image.
do_install() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    cp -r ${S}/yt_dlp_ejs ${D}${PYTHON_SITEPACKAGES_DIR}/
}

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}"

RDEPENDS:${PN} = "python3-core"

# The split below moves every .py into the -src package, which no image carries,
# so byte compile the package and ship that.
require conf/python/python3-compileall.inc

include python3-package-split.inc
