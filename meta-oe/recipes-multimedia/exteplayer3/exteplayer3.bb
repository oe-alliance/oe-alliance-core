SUMMARY = "exteplayer3 - media player for E2"
DESCRIPTION = "Core of movie player for E2 based on the libeplayer using the ffmpeg solution"
SECTION = "multimedia"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "ffmpeg-ext zlib bzip2 libxml2 xz libbluray openssl librtmp libudfread"
RDEPENDS:${PN} += "ffmpeg-ext-libs libxml2 zlib bzip2 liblzma libbluray openssl librtmp libudfread"

inherit gittag autotools pkgconfig upx-compress

SRCREV = "${AUTOREV}"
# version from the git tag of the player (v188 ...); the OpenBH and OpenViX
# repositories have no tags yet and keep the fixed version
PV = "git"
PKGV = "${GITPKGVTAG}"
PV:openbh = "185+git"
PKGV:openbh = "185+git${GITPKGV}"
PV:openvix = "185+git"
PKGV:openvix = "185+git${GITPKGV}"
# r1: rebuilt against ffmpeg-ext 9 (libavformat.so.63 ... instead of .so.60)
PR = "r1"

SRC_URI:openbh = "git://github.com/BlackHole/exteplayer3.git;branch=master;protocol=https"
SRC_URI:openvix = "git://github.com/OpenViX/exteplayer3.git;branch=master;protocol=https"
SRC_URI = "git://github.com/oe-alliance/exteplayer3.git;branch=master;protocol=https"

EXTRA_OECONF = ""

PACKAGECONFIG ??= ""
PACKAGECONFIG:append:dreamone = " dreamnextgen"
PACKAGECONFIG:append:dreamtwo = " dreamnextgen"
PACKAGECONFIG:append:gtxcombo = " dreamnextgen"
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

# ffmpeg-ext has the sonames of the system FFmpeg 9 and is not registered as
# their shlib provider: the player depends on ffmpeg-ext-libs (above), not on
# the libav* packages of the system FFmpeg.
PRIVATE_LIBS = "libavcodec.so.* libavdevice.so.* libavfilter.so.* libavformat.so.* \
                libavutil.so.* libswresample.so.* libswscale.so.*"
