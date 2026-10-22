SRC_URI[meson64.md5sum] = "180270b3c9780b07aa3df139086da018"
SRC_URI[meson64.sha256sum] = "f60ed1693d1af7e876249ec9dbfa670bf76d2b00dabf9619748ecf5935f9cd65"

PRECOMPILED_NAME = "dreambox-dvb-modules"
# one driver build for the Dreambox One and Two
PRECOMPILED_ARCH = "meson64"

require dreambox-dvb-modules-meson64.inc

RREPLACES:${PN} += "dreamframeinject"
RPROVIDES:${PN} += "dreamframeinject"
RCONFLICTS:${PN} += "dreamframeinject"

COMPATIBLE_MACHINE = "^(dms922x)$"

# The multi instance H.264 and MPEG-2 decoders arm their check timer for
# every run without STAT_TIMER_ARM, and their remove deletes the timer only
# with that flag. A decoder removed during a run (PiP stop, retune) is freed
# with the timer pending, which then crashes or locks up the timer softirq.
# Delete the timer always: the branches on the flag in the remove functions
# become plain ones.
#
# meson64 maps the stream type 7 to RealVideo, so the AVS decoder is out of
# reach: everything else it does not map goes to the decoder as the vformat
# itself. RealVideo moves to 31, the stream type 7 reaches VFORMAT_AVS.
#
# The AVS decoder resets its core in remove only from TM2 on. A restart of
# the decoder (VIDEO_CLEAR_BUFFER on every zap and jump) then sets up the
# stream buffer again while the VLD still fetches and locks the bus: reset
# the core always.
#
# meson64 waits for room in the stream buffer up to 75 ms inside the
# buffer of the vdec, with its timer and wait queue. A decoder restart
# (VIDEO_CLEAR_BUFFER) frees the vdec while a writer still waits there, the
# timer then fires in freed memory: seen with AVS+, whose decoder stops
# taking data, at the first PTS jump. decoder_wait_free_space now only
# checks for room and sleeps 1 ms outside the buffer when there is none, the
# callers retry as before; the _mcount call becomes the msleep call.
patch_ko() {
    ko=${D}/lib/modules/${DM_LOCALVERSION}/extra/$1
    # do_install hard links ${D} to ${S}, patch a copy of our own
    cp $ko $ko.tmp && mv $ko.tmp $ko
    test "$(od -An -tx1 -j $2 -N4 $ko | tr -d ' \n')" = "$3"
    printf "$4" | dd of=$ko bs=1 seek=$2 conv=notrunc
}

do_install:append() {
    dec=${D}/lib/modules/${DM_LOCALVERSION}/extra/aml-4.9/frame_provider/decoder
    echo "078f726bb5a4aff022abbf56905bcc2d  $dec/h264_multi/amvdec_mh264.ko" | md5sum -c -
    # ammvdec_h264_remove: tbnz w0, #4 -> b, .text+0xf854
    patch_ko aml-4.9/frame_provider/decoder/h264_multi/amvdec_mh264.ko 63676 40192037 '\312\000\000\024'
    echo "17b16809aa5cef96a2cb1be9cd9c882b  $dec/h264_multi/amvdec_mh264.ko" | md5sum -c -

    echo "2627ec867d36a7641808170bad661080  $dec/mpeg12/amvdec_mmpeg12.ko" | md5sum -c -
    # ammvdec_mpeg12_remove: tbnz w0, #4 -> b, .text+0x1b8
    patch_ko aml-4.9/frame_provider/decoder/mpeg12/amvdec_mmpeg12.ko 544 a00d2037 '\155\000\000\024'
    # ammvdec_mpeg12_remove: tbz w0, #4 -> nop, .text+0x368
    patch_ko aml-4.9/frame_provider/decoder/mpeg12/amvdec_mmpeg12.ko 976 a0f22736 '\037\040\003\325'
    echo "8c9c920e912d24f1dbe7c475a7a517b6  $dec/mpeg12/amvdec_mmpeg12.ko" | md5sum -c -

    echo "6ea71ca2678aef24a9b2525e8b027d18  $dec/avs/amvdec_avs.ko" | md5sum -c -
    # amvdec_avs_remove: b.hi -> b, .text+0x5cc
    patch_ko aml-4.9/frame_provider/decoder/avs/amvdec_avs.ko 1588 28080054 '\101\000\000\024'
    echo "78172935e2c75b14f59988bf8582e4d0  $dec/avs/amvdec_avs.ko" | md5sum -c -

    core=${D}/lib/modules/${DM_LOCALVERSION}/extra/core
    echo "f71b009e1c7f68c3ba0fe70a70ac5b87  $core/meson64.ko" | md5sum -c -
    # dvb_video_set_streamtype: cmp w20, #0x7 -> cmp w20, #0x1f, .text+0x3c94
    patch_ko core/meson64.ko 15612 9f1e0071 '\237\176\000\161'
    echo "26682e8015ef1652fa360e90c7f90603  $core/meson64.ko" | md5sum -c -
    # decoder_wait_free_space: no wait in the stream buffer, .text+0x7938
    patch_ko core/meson64.ko 31136 fd7bbea9 '\375\173\276\251\375\003\000\221\000\010\100\371\002\000\200\322\041\174\100\223\010\000\000\024\000\000\000\224\100\001\200\022\012\000\000\024\040\000\200\122\374\377\377\027\037\040\003\325\037\040\003\325\000\100\001\221\037\040\003\325\000\000\000\224\037\054\000\061\000\377\377\124\375\173\302\250\300\003\137\326'
    # its relocation at .text+0x7950: symbol _mcount (1397) -> msleep (1402)
    patch_ko core/meson64.ko 1269664 1b010000 '\033\001\000\000\172\005'
    echo "404cce0f3c13acf9335df234a35018c1  $core/meson64.ko" | md5sum -c -
}
