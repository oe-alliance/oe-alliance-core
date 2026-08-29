SUMMARY = "Kodi inputstream add-on using FFmpeg or Kodi cURL"
DESCRIPTION = "Provides TS, HLS and non-DRM DASH input plus archive, catch-up and live timeshift support."
HOMEPAGE = "https://github.com/xbmc/inputstream.ffmpegdirect"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

DEPENDS += "bzip2 ffmpeg zlib"

SRCREV = "6c02cd986df473592cbcdb419cd7a357ba020f2c"
PV = "22.2.7+gitr"

KODIADDONBRANCH = "Piers"
KODIADDONNAME = "inputstream.ffmpegdirect"

SRC_URI = "git://github.com/xbmc/inputstream.ffmpegdirect.git;protocol=https;branch=${KODIADDONBRANCH}"

# Reuse the FFmpeg already built for Kodi.  Never fall back to the add-on's
# large internal dependency build in a receiver feed.
EXTRA_OECMAKE += "-DENABLE_INTERNAL_FFMPEG=OFF -DFFMPEG_PATH=${STAGING_DIR_HOST}${prefix}"

INSANE_SKIP:${PN} = "libdir dev-so file-rdeps"
