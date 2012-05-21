PRINC = "1"

do_compile_append_mipsel() {
	echo "arch ${TARGET_ARCH} $priority" >> $archconf
}
