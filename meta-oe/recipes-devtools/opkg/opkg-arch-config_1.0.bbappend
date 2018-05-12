PACKAGE_ARCH = "${MACHINEBUILD}"
PR = "r6"

LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

do_compile_append_mipsel() {
    echo "arch ${TARGET_ARCH} $priority" >> $archconf
}

# add support for extra feeds
PACKAGE_ARCHS += " ocram"

do_compile_append_dm800() {
    echo "arch mips32el $priority" >> $archconf
}

do_compile_append_bcm7376() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
}

do_compile_append_bcm7439() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
}

do_compile_append_bcm7251s() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
}

do_compile_append_bcm7252s() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
}

do_compile_append_bcm72604() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
}

do_compile_append_bcm7444() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
}

do_compile_append_hisi3798mv200() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
    echo "arch cortexa15hf-neon-vfpv4 $priority" >> $archconf
}

do_compile_append_hisi3798cv200() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
    echo "arch cortexa15hf-neon-vfpv4 $priority" >> $archconf
}

do_compile_append_sh4() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
}

do_compile_append_cube() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
}

do_compile_append_AML8726() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
}

do_compile_append_AMLS905() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
}

do_compile_append_AML905D() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
}
