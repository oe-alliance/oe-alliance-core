SUMMARY = "Audio decoding service for the DreamOne/DreamTwo"
DESCRIPTION = "The SoC has no audio decoder. The kernel offers one per audio decoder of the DVB API; this service decodes or packs the audio for it and writes it with its PTS to the S/PDIF driver, which paces the output."
require conf/license/license-gplv2.inc

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"
PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "alsa-lib faad2 ffmpeg virtual/kernel"

SRC_URI = " \
    file://dreamaudiod.cpp \
    file://dreamaudiod.init \
"

S = "${UNPACKDIR}"

inherit update-rc.d

INITSCRIPT_NAME = "dreamaudiod"
INITSCRIPT_PARAMS = "start 40 S . stop 40 0 6 ."

# the uapi header of the dreamaudio device comes from the kernel source
do_configure[depends] += "virtual/kernel:do_shared_workdir"

do_configure() {
    install -m 0644 ${STAGING_KERNEL_DIR}/include/uapi/linux/dvb/dreamaudio.h ${S}/dreamaudio.h
}

do_compile() {
    ${CXX} ${CXXFLAGS} ${LDFLAGS} -std=c++17 -Wall -o dreamaudiod dreamaudiod.cpp \
        -lavformat -lavcodec -lswresample -lavutil -lfaad -lasound -lm
}

do_install() {
    install -d ${D}${bindir} ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/dreamaudiod ${D}${bindir}/dreamaudiod
    install -m 0755 ${S}/dreamaudiod.init ${D}${sysconfdir}/init.d/dreamaudiod
}
