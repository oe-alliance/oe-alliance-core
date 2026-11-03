FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# Both fixes are needed on the HiSilicon machines, which drive a plain
# framebuffer instead of the proprietary hifb accelerator.
SRC_URI:append:sf8008 = " \
    file://0001-bcm-reset-displaylist-when-exec_list-cannot-flush.patch \
    file://0002-gfbdc-do-not-swap-in-an-unallocated-back-surface.patch \
"
