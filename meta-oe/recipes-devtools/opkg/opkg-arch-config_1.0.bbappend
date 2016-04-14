PACKAGE_ARCH = "${MACHINEBUILD}"
PR = "r5"

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

do_compile_append_sh4() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
}

do_compile_append_wetekplay() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch cortexa9hf-vfp-neon $priority" >> $archconf
}
