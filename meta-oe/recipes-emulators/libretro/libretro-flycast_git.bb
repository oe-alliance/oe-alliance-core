SUMMARY = "Flycast GLES2 libretro core"
DESCRIPTION = "Experimental Sega Dreamcast, Naomi and Atomiswave emulator core."
HOMEPAGE = "https://github.com/flyinghead/flycast"

LICENSE = "GPL-2.0-only"
PR = "r1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "gitsm://github.com/flyinghead/flycast.git;protocol=https;branch=master"
SRCREV = "0d9853df9a917eba2f7a84158bd1c3bccb8933e8"
PV = "0.1+git20261008.${SRCPV}"

inherit cmake
require libretro-core.inc

DEPENDS = "libzip virtual/egl virtual/libgles2 zlib"
RDEPENDS:${PN} += "virtual-libgles2 virtual-egl"

LIBRETRO_CORE_FILE = "flycast_libretro.so"
LIBRETRO_CORE_PATH = "${B}/${LIBRETRO_CORE_FILE}"

EXTRA_OECMAKE = " \
    -DLIBRETRO=ON \
    -DUSE_GLES2=ON \
    -DUSE_GLES=OFF \
    -DUSE_OPENGL=ON \
    -DUSE_VULKAN=OFF \
    -DUSE_HOST_LIBZIP=ON \
    -DUSE_HOST_SDL=OFF \
    -DUSE_OPENMP=OFF \
    -DUSE_BREAKPAD=OFF \
    -DUSE_LUA=OFF \
    -DUSE_DISCORD=OFF \
    -DUSE_ALSA=OFF \
    -DUSE_LIBAO=OFF \
    -DUSE_PULSEAUDIO=OFF \
"
