SUMMARY = "mGBA libretro core"
DESCRIPTION = "Nintendo Game Boy Advance emulator core, also supporting Game Boy and Game Boy Color."
HOMEPAGE = "https://github.com/libretro/mgba"

LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=815ca599c9df247a0c7f619bab123dad"

SRC_URI = "git://github.com/libretro/mgba.git;protocol=https;branch=master"
SRCREV = "7a12d6d4b9acb14c0ae62c9166b6a2f3d08007f6"
PV = "0.1+git20261008.${SRCPV}"

inherit cmake
require libretro-core.inc

DEPENDS = "zlib"

LIBRETRO_CORE_FILE = "mgba_libretro.so"
LIBRETRO_CORE_PATH = "${B}/${LIBRETRO_CORE_FILE}"

EXTRA_OECMAKE = " \
    -DBUILD_LIBRETRO=ON \
    -DBUILD_QT=OFF \
    -DBUILD_SDL=OFF \
    -DBUILD_GL=OFF \
    -DBUILD_GLES2=OFF \
    -DBUILD_GLES3=OFF \
    -DUSE_DISCORD_RPC=OFF \
    -DUSE_EDITLINE=OFF \
    -DUSE_EPOXY=OFF \
    -DUSE_FFMPEG=OFF \
    -DUSE_LIBZIP=OFF \
    -DUSE_LUA=OFF \
    -DUSE_MINIZIP=OFF \
    -DUSE_PNG=OFF \
    -DUSE_SQLITE3=OFF \
    -DUSE_ZLIB=ON \
"
