PACKAGE_ARCH = "${MACHINEBUILD}"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

do_compile:append:mipsel() {
    echo "arch ${TARGET_ARCH} $priority" >> $archconf
}

# add support for extra feeds
PACKAGE_ARCHS += " ocram"

do_compile:append:dm800() {
    echo "arch mips32el $priority" >> $archconf
}

do_compile:append:bcm7376() {
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
}

do_compile:append:bcm7439() {
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
}

do_compile:append:bcm7251s() {
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
}

do_compile:append:bcm7252s() {
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
}

do_compile:append:bcm72604() {
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
}

do_compile:append:bcm7444() {
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
}

do_compile:append:hisi3798mv200() {
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
    echo "arch cortexa15hf-neon-vfpv4 $priority" >> $archconf
}

do_compile:append:hisi3798mv300() {
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
    echo "arch cortexa15hf-neon-vfpv4 $priority" >> $archconf
}

do_compile:append:3798mv200advca() {
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
    echo "arch cortexa15hf-neon-vfpv4 $priority" >> $archconf
}

do_compile:append:hisi3798mv200h() {
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
    echo "arch cortexa15hf-neon-vfpv4 $priority" >> $archconf
}

do_compile:append:hisi3798mv310() {
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
    echo "arch cortexa15hf-neon-vfpv4 $priority" >> $archconf
}

do_compile:append:hisi3716mv430() {
    echo "arch armv7ahf $priority" >> $archconf
}

do_compile:append:hisi3798cv200() {
    echo "arch armv7ahf-vfp-neon $priority" >> $archconf
    echo "arch armv7ahf-neon $priority" >> $archconf
    echo "arch cortexa15hf-neon-vfpv4 $priority" >> $archconf
}

do_compile:append:AML8726() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
}

do_compile:append:AMLS905() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
}

do_compile:append:AML905D() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
}
