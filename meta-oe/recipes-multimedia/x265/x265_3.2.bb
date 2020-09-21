DESCRIPTION = "x265 HEVC Encoder / H.265 Video Codec"
SUMMARY = "x265 is a H.265 / HEVC video encoder application library, designed to encode video or images into an H.265 / HEVC encoded bitstream."
HOMEPAGE = "http://x265.org/"
SECTION = "multimedia"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/x265_${PV}/COPYING;md5=c9e0427bc58f129f99728c62d4ad4091"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

DEPENDS = "yasm"

SRC_URI = "https://ftp.fau.de/videolan/x265/x265_${PV}.tar.gz \
        file://set-multilib-paths.patch"
SRC_URI[md5sum] = "374e6359a00d17fd82195c02c341c861"
SRC_URI[sha256sum] = "364d79bcd56116a9e070fdeb1d9d2aaef1a786b4970163fb56ff0991a183133b"

S = "${WORKDIR}/x265_${PV}/source"

inherit cmake

OECMAKE_GENERATOR = "Unix Makefiles"

TARGET_CFLAGS += "-fPIC"
