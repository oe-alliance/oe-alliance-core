SUMMARY = "Amlogic transport stream input, hardware demux and video decoder modules"
require conf/license/license-close.inc
GTX_VIDEO_FIRMWARE_LICENSE := "${LICENSE}"
GTX_MODULE_LICENSE = "GPL-2.0-or-later"
LICENSE = "${GTX_MODULE_LICENSE} AND ${GTX_VIDEO_FIRMWARE_LICENSE}"
LICENSE:${PN}-video-firmware = "${GTX_VIDEO_FIRMWARE_LICENSE}"
LIC_FILES_CHKSUM += "file://drivers/stream_input/parser/hw_demux/aml_dvb.c;beginline=1;endline=16;md5=baca0f0ce6d0bfa6fea2762a8580620c"
COMPATIBLE_MACHINE = "^gtxcombo$"

SRC_URI = "git://github.com/oe-alliance-kernels/gtx-media-modules.git;protocol=https;branch=5.15.196"
SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "git${GITPKGV}"
PR = "r1"
S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}"

inherit gitpkgv module
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/modules.order.*"

# Build the stream input, clock and decoder dependencies in the same modpost run.
MAKE_TARGETS = "modules"
EXTRA_OEMAKE += "-C ${STAGING_KERNEL_DIR} M=${S}/drivers CONFIG_AMLOGIC_HW_DEMUX=m \
    CONFIG_AMLOGIC_MEDIA_VDEC_MPEG2_MULTI=m CONFIG_AMLOGIC_MEDIA_VDEC_MPEG4_MULTI=m \
    CONFIG_AMLOGIC_MEDIA_VDEC_H264_MULTI=m CONFIG_AMLOGIC_MEDIA_VDEC_H265=m \
    CONFIG_AMLOGIC_MEDIA_VDEC_VP9=m CONFIG_AMLOGIC_MEDIA_VDEC_MJPEG_MULTI=m \
    CONFIG_AMLOGIC_MEDIA_VENC_COMMON=m CONFIG_AMLOGIC_MEDIA_VENC_H264=m CONFIG_AMLOGIC_MEDIA_VENC_H265=m"
MODULES_MODULE_SYMVERS_LOCATION = "drivers"
KERNEL_MODULE_AUTOLOAD += "encoder vpu aml_hardware_dmx amvdec_mmpeg12 amvdec_mmpeg4 amvdec_mh264 amvdec_h265 amvdec_vp9 amvdec_mmjpeg"

do_compile:append() {
    for driver in stream_input/stream_input stream_input/parser/hw_demux/aml_hardware_dmx \
            frame_provider/decoder/h264_multi/amvdec_mh264 frame_provider/decoder/h265/amvdec_h265 \
            frame_sink/encoder/common/encoder_common frame_sink/encoder/h264/encoder frame_sink/encoder/h265/vpu; do
        test -s "${S}/drivers/$driver.ko" || bbfatal "Kbuild did not produce $driver.ko"
    done
}

# firmware_drv requests video/video_ucode.bin through request_firmware().
PACKAGES =+ "${PN}-video-firmware"
FILES:${PN}-video-firmware = "${nonarch_base_libdir}/firmware/video/video_ucode.bin"

# kernel-module-split has added the individual module packages by this point.
python populate_packages:prepend() {
    for package in d.getVar("PACKAGES").split():
        if not d.getVar("LICENSE:" + package):
            d.setVar("LICENSE:" + package, d.getVar("GTX_MODULE_LICENSE"))
}

# The SM1 DT binds aml_hardware_dmx. The SC2 demux exports the same adapter symbol.
RDEPENDS:${PN} += "${PN}-video-firmware kernel-module-aml-hardware-dmx"
RDEPENDS:${PN} += "kernel-module-encoder-common kernel-module-encoder kernel-module-vpu"
do_install:append() {
    install -Dm0644 ${S}/firmware/video_ucode.bin ${D}${nonarch_base_libdir}/firmware/video/video_ucode.bin

    # Player and mosaic build against the same decoder headers and symbols.
    # Preserve the relative includes used by vdec.h and streambuf.h.
    cd ${S}/drivers
    for header in \
            amvdec_ports/aml_task_chain.h amvdec_ports/aml_vcodec_avbc_wrapper.h \
            amvdec_ports/utils/common.h amvdec_ports/utils/pixfmt.h \
            common/media_clock/switch/amports_gate.h common/media_utils/media_kernel_version.h \
            common/media_utils/media_utils.h common/register/register.h \
            frame_provider/decoder/utils/decoder_report.h frame_provider/decoder/utils/frame_check.h \
            frame_provider/decoder/utils/vdec.h frame_provider/decoder/utils/vdec_canvas_utils.h \
            frame_provider/decoder/utils/vdec_input.h frame_provider/decoder/utils/vdec_sync.h \
            framerate_adapter/video_framerate_adapter.h include/regs/dos_registers*.h \
            media_sync/pts_server/pts_server_core.h stream_input/amports/stream_buffer_base.h \
            stream_input/amports/streambuf.h stream_input/amports/streambuf_reg.h; do
        install -Dm0644 "$header" "${D}${includedir}/${BPN}/drivers/$header"
    done
}
