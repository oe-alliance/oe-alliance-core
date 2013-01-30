PRINC = "5"
PACKAGE_ARCH = "${MACHINE_ARCH}"

# add support for extra feeds
PACKAGE_ARCHS += " ocram"

do_compile_append_odinm9() {
	echo "arch et9x00 $priority" >> $archconf
}
