PRINC = "9"
PACKAGE_ARCH = "${MACHINEBUILD}"

do_compile_append_mipsel() {
    echo "arch ${TARGET_ARCH} $priority" >> $archconf
}

# add support for extra feeds
PACKAGE_ARCHS += " ocram"

do_compile_append_odinm9() {
    echo "arch et9x00 $priority" >> $archconf
}

do_compile_append_cube() {
	echo "arch mips32el $priority" >> $archconf
	echo "arch mipsel $priority" >> $archconf
}
