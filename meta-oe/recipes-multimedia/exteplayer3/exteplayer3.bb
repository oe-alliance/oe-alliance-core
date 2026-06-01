SUMMARY = "exteplayer3 - media player for E2"
DESCRIPTION = "Core of movie player for E2 based on the libeplayer using the ffmpeg solution"
SECTION = "multimedia"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "ffmpeg-ext zlib bzip2 libxml2 xz libbluray openssl librtmp libudfread"
RDEPENDS:${PN} += "ffmpeg-ext-libs libxml2 zlib bzip2 liblzma libbluray openssl librtmp libudfread"

inherit gitpkgv autotools pkgconfig upx-compress

SRCREV = "${AUTOREV}"
PV = "181+git"
PKGV = "181+git${GITPKGV}"

SRC_URI = "git://github.com/oe-alliance/exteplayer3.git;branch=master;protocol=https"

EXTRA_OECONF = ""

PACKAGECONFIG ??= ""
PACKAGECONFIG:append:dreamone = " dreamnextgen"
PACKAGECONFIG:append:dreamtwo = " dreamnextgen"
PACKAGECONFIG[dreamnextgen] = "--enable-dreamnextgen,--disable-dreamnextgen,alsa-lib"

LDFLAGS:append = " \
    -L${STAGING_LIBDIR}/ffmpeg-ext \
    -Wl,-rpath,/usr/lib/ffmpeg-ext \
    -Wl,-rpath-link,${STAGING_LIBDIR}/ffmpeg-ext \
"

# ffmpeg-ext ships its own pkg-config dir under libdir/ffmpeg-ext/pkgconfig
# — make configure see it ahead of the system ffmpeg .pc.
do_configure:prepend() {
    export PKG_CONFIG_PATH="${STAGING_LIBDIR}/ffmpeg-ext/pkgconfig:${PKG_CONFIG_PATH}"
}

INSANE_SKIP:${PN} += "ldflags rpaths"
