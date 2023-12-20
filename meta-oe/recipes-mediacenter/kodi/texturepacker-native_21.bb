SUMMARY = "Kodi Media Center"

LICENSE = "CLOSED"

FILESPATH =. "${FILE_DIRNAME}/kodi-19:"


DEPENDS = " \
    cmake-native \
    giflib-native \
    libpng-native \
    lzo-native \
    libpng \
    libjpeg-turbo \
    libjpeg-turbo-native \
"

SRCREV = "f44fdfbf675f30c01e7639177a34544e6a6b9dad"

# 'patch' doesn't support binary diffs
#PATCHTOOL = "git"

PV = "21.0+gitr"
SRC_URI = "git://github.com/xbmc/xbmc.git;branch=master;protocol=https \
          "

inherit autotools-brokensep gettext pkgconfig native

S = "${WORKDIR}/git/tools/depends/native/TexturePacker/src"

#OECMAKE_CXX_FLAGS_append = " -DTARGET_POSIX -DTARGET_LINUX -D_LINUX -std=gnu++11 -I${WORKDIR}/git/xbmc/linux"

do_configure:prepend() {
    sed -i '/STATIC_FLAG/d' ${S}/Makefile.am
}

BBCLASSEXTEND = "native"
