FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# These fixes are needed on the HiSilicon machines, which drive a plain
# framebuffer instead of the proprietary hifb accelerator.
SRC_URI:append:sf8008 = " \
    file://0001-bcm-reset-displaylist-when-exec_list-cannot-flush.patch \
    file://0002-gfbdc-start-drawing-on-the-page-that-is-displayed.patch \
    file://0003-gfbdc-present-the-page-that-was-just-rendered.patch \
"
