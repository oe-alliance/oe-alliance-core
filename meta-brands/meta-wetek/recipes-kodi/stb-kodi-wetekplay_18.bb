require recipes-mediacenter/kodi/stb-kodi_${PV}.bb

SRC_URI_remove = "file://e2player.patch \
           file://0001-introduce-basic-GstPlayer.patch \
           file://stb-support.patch \
"

SRC_URI_append += " \
    file://001-add-linuxinputdevice.patch \
"

PROVIDES += "virtual/kodi"
RPROVIDES_${PN} += "virtual/kodi"
PROVIDES += "kodi"
RPROVIDES_${PN} += "kodi"
DEPENDS_append = " wetek-libamadec-${MACHINE} wetek-libamcodec-${MACHINE} wetek-libamavutils-${MACHINE}"

CXXFLAGS += " -I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include -I${STAGING_INCDIR} -I${STAGING_INCDIR}/amlogic "
CFLAGS += " -I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include -I${STAGING_INCDIR} -I${STAGING_INCDIR}/amlogic "

WINDOWSYSTEM = "amlogic"

EXTRA_OECONF += " \
    -DENABLE_AML=ON \
    -DHAS_LIBAMCODEC=1 \
    -DENABLE_OPENGLES=ON \
    -DENABLE_OPENGL=OFF \
    -DWITH_ARCH=arm \
    -DCORE_PLATFORM_NAME=aml \
    -DCORE_SYSTEM_NAME=linux \
    -DENABLE_INTERNAL_FFMPEG=ON \
"

EXTRA_OECONF_append_armv7a = "--cpu=cortex-a9"

INSANE_SKIP_${PN} += "file-rdeps"
