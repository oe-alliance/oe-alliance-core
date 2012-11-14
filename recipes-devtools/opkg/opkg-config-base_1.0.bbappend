PRINC = "3"
PACKAGE_ARCH = "${MACHINE_ARCH}"

do_compile_append_maram9() {
	echo "arch et9x00 $priority" >> $archconf
}
