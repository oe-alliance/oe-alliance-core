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

SRCREV = "c23b69d150637747de6432410d4c5f1df69a3252"

# 'patch' doesn't support binary diffs
#PATCHTOOL = "git"

PV = "19.0+gitr${SRCPV}"
SRC_URI = "git://github.com/xbmc/xbmc.git;branch=master \
          "

inherit autotools-brokensep native gettext

S = "${WORKDIR}/git/tools/depends/native/TexturePacker/src"

#OECMAKE_CXX_FLAGS_append = " -DTARGET_POSIX -DTARGET_LINUX -D_LINUX -std=gnu++11 -I${WORKDIR}/git/xbmc/linux"

do_configure_prepend() {
    sed -i '/STATIC_FLAG/d' ${S}/Makefile.am
}


