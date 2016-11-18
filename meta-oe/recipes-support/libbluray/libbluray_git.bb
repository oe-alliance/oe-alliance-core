SUMMARY  = "Library to access Blu-Ray disk"
SECTION = "misc"
HOMEPAGE = "http://videolan.org"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=435ed639f84d4585d93824e7da3d85da"

DEPENDS = "libxml2"

SRC_URI = "gitsm://git.videolan.org/libbluray.git \
    file://expose_clip_id_in_BLURAY_CLIP_INFO.patch \
"
SRCREV="efcde25b3bd58eee9cb96f151b79a585a52a09ff"

inherit gitpkgv autotools-brokensep pkgconfig

# Set PV and PKGV manualy on OE before https://github.com/openembedded/meta-openembedded/commit/22004e5281a913818a94bcd160ad3135a9ecd314
# Remove this after meta-openembedded update
#PV = "v0.9.3+git${SRCPV}"
#PKGV = "v0.9.3+git${GITPKGV}"
PV = "v0.9.3+git2490+efcde25"
PKGV = "v0.9.3+git2490+efcde25"

S="${WORKDIR}/git"

EXTRA_OECONF = " \
    --disable-bdjava \
    --disable-bdjava-jar \
    --disable-doxygen-doc \
    --disable-doxygen-dot \
    --without-freetype \
    --without-fontconfig \
"

do_package_qa() {
}
