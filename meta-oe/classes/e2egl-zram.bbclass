# Enable the kernel side of ZRAM for every machine that opts into e2egl.
#
# Vendor defconfigs differ widely: some already build ZRAM as a module, some
# explicitly disable ZSMALLOC, and DreamNextGen builds ZRAM into the kernel.
# Applying the final setting after the provider's normal do_configure keeps the
# policy tied to MACHINE_FEATURES instead of maintaining duplicate model lists.

python __anonymous() {
    provides = (d.getVar("PROVIDES") or "").split()
    if "virtual/kernel" in provides:
        d.appendVarFlag("do_configure", "postfuncs", " e2egl_zram_config")
}

e2egl_zram_config() {
	config="${B}/.config"
	[ -f "$config" ] || bbfatal "e2egl ZRAM: kernel configuration not found: $config"

	sed -i \
		-e '/^CONFIG_ZSMALLOC=/d' \
		-e '/^# CONFIG_ZSMALLOC is not set/d' \
		-e '/^CONFIG_ZRAM=/d' \
		-e '/^# CONFIG_ZRAM is not set/d' \
		"$config"

	echo "CONFIG_ZSMALLOC=y" >> "$config"
	if [ "${SOC_FAMILY}" = "meson64" ]; then
		echo "CONFIG_ZRAM=y" >> "$config"
		expected="CONFIG_ZRAM=y"
	else
		echo "CONFIG_ZRAM=m" >> "$config"
		expected="CONFIG_ZRAM=m"
	fi

	if [ "${S}" = "${B}" ]; then
		yes '' | oe_runmake oldconfig
	else
		yes '' | oe_runmake -C "${S}" O="${B}" oldconfig
	fi

	grep -qx "CONFIG_ZSMALLOC=y" "$config" ||
		bbfatal "e2egl ZRAM: kernel rejected CONFIG_ZSMALLOC=y"
	grep -qx "$expected" "$config" ||
		bbfatal "e2egl ZRAM: kernel rejected $expected"
}
