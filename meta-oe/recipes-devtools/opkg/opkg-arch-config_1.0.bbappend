PACKAGE_ARCH = "${MACHINEBUILD}"
PR = "r7"

do_compile_append_mipsel() {
    echo "arch ${TARGET_ARCH} $priority" >> $archconf
}

# add support for extra feeds
PACKAGE_ARCHS += " ocram"

do_compile_append_odinm9() {
    echo "arch et9x00 $priority" >> $archconf
}

do_compile_append_dm800() {
    echo "arch mips32el $priority" >> $archconf
}

do_compile_append_vusolo4k() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
}

do_compile_append_vuuno4k() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
}

do_compile_append_dm900() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
}

do_compile_append_vuultimo4k() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
}

do_compile_append_sf4008() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
}

do_compile_append_hd51() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
}

do_compile_append_vs1500() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
}

do_compile_append_hd52() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
}

do_compile_append_sh4() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
}

do_compile_append_wetekplay() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch cortexa9hf-vfp-neon $priority" >> $archconf
}

do_compile_append_wetekplay2() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch cortexa9hf-vfp-neon $priority" >> $archconf
}
