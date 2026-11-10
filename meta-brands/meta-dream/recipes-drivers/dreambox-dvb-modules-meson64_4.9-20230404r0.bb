SRC_URI[meson64.md5sum] = "180270b3c9780b07aa3df139086da018"
SRC_URI[meson64.sha256sum] = "f60ed1693d1af7e876249ec9dbfa670bf76d2b00dabf9619748ecf5935f9cd65"

PRECOMPILED_NAME = "dreambox-dvb-modules"
# one driver build for the Dreambox One and Two
PRECOMPILED_ARCH = "meson64"

PR = "r4"

require dreambox-dvb-modules-meson64.inc

RREPLACES:${PN} += "dreamframeinject"
RPROVIDES:${PN} += "dreamframeinject"
RCONFLICTS:${PN} += "dreamframeinject"

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"

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
#
# That retry loop in dvb_video_write ends only on the abort flag of the
# decoder, not on a signal. A dvr write into a paused, full decoder then
# blocks for good, and stopping the writer (end of a paused timeshift)
# hangs. decoder_should_abort also reports a pending signal, so the write
# returns -EINTR as with an interruptible wait; its _mcount relocation goes.
#
# Both functions keep their entry in __mcount_loc, while their _mcount call
# is gone. ftrace finds no call to _mcount there when the module loads and
# warns with a backtrace; the two entries become R_AARCH64_NONE, ftrace
# skips a location of 0.
#
# aml_vtuner_pid_action stops a PID on the vtuner the demux is fed from
# now, not on the one it was started on. A section filter started while the
# demux was fed by a tuner and stopped after the switch to a vtuner (a zap
# from a descrambled satellite channel to a USB tuner, the softcam closes
# its ECM filter late) is not in the PID list: the function hits BUG() with
# the vtuner mutex held, and every later PID change on that vtuner blocks.
# The BUG() becomes the regular exit, which unlocks the mutex; the log of
# the PID list stays.
#
# decoder_poll reads the vdec of the decoder twice, without a lock and
# without a check. A poll of the dvr while the decoder closes finds it NULL
# and crashes in stbuf_level. The vdec is now read once and checked; the
# _mcount call makes room for that, its relocation and __mcount_loc entry
# become R_AARCH64_NONE.
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
    # decoder_should_abort: abort flag or TIF_SIGPENDING, .text+0x7820
    patch_ko core/meson64.ko 30856 fd7bbea9 '\001\240\100\271\002\101\070\325\102\000\100\371\102\000\000\022\040\000\002\052\037\040\003\325\300\003\137\326\037\040\003\325\037\040\003\325\037\040\003\325'
    # its relocation at .text+0x7834: _mcount -> R_AARCH64_NONE
    patch_ko core/meson64.ko 1269592 1b010000 '\000\000\000\000\000\000\000\000'
    echo "1176b7ddc25e29e0a3acae44548d801a  $core/meson64.ko" | md5sum -c -
    # __mcount_loc entries for .text+0x7834 and .text+0x7950 -> R_AARCH64_NONE
    patch_ko core/meson64.ko 1303936 01010000 '\000\000\000\000\000\000\000\000'
    patch_ko core/meson64.ko 1303984 01010000 '\000\000\000\000\000\000\000\000'
    echo "a8d37f971518ee44c8181f2051cd46fe  $core/meson64.ko" | md5sum -c -
    # aml_vtuner_pid_action: brk #0x800 -> b .text+0xac60, .text+0xad1c
    patch_ko core/meson64.ko 44420 000021d4 '\321\377\377\027'
    echo "3fde08003c13e88572bf586f17276449  $core/meson64.ko" | md5sum -c -
    # decoder_poll: read the vdec once and check it, .text+0x785c
    patch_ko core/meson64.ko 30916 e0031eaa '\365\003\001\252\364\003\002\252\277\002\000\361\204\032\100\372\200\001\000\124\203\002\100\371\141\002\002\221\177\000\000\361\044\030\100\372\200\000\000\124\342\003\024\252\340\003\025\252\140\000\077\326\140\172\100\271\037\004\000\161\300\004\000\124\024\000\200\122\165\012\100\371\225\001\000\264\240\102\001\221\037\040\003\325'
    # the second read, .text+0x78c0
    patch_ko core/meson64.ko 31016 610a40f9 '\341\003\025\252'
    # the branch back to the read, .text+0x7934
    patch_ko core/meson64.ko 31132 ddffff17 '\333\377\377\027'
    # relocation and __mcount_loc entry of .text+0x7868 -> R_AARCH64_NONE
    patch_ko core/meson64.ko 1269616 1b010000 '\000\000\000\000\000\000\000\000'
    patch_ko core/meson64.ko 1303960 01010000 '\000\000\000\000\000\000\000\000'
    echo "5ba6953e2f622d7281fe1460a199dd96  $core/meson64.ko" | md5sum -c -
}
