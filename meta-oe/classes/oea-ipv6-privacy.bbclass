# Enable IPv6 privacy extensions for every supported receiver kernel.
#
# CONFIG_IPV6_PRIVACY was removed as a separate upstream option in Linux 3.12,
# when its functionality became unconditional. Vendor kernels do not always
# follow that exact boundary, so detect the symbol in their Kconfig instead of
# relying only on the reported kernel version.

python __anonymous() {
    provides = (d.getVar("PROVIDES") or "").split()
    version = d.getVar("PV") or "0"
    if "virtual/kernel" in provides and bb.utils.vercmp_string_op(version, "3.2", ">="):
        d.appendVarFlag("do_configure", "postfuncs", " oea_ipv6_privacy_config")
        if "ipv6" not in (d.getVar("KERNEL_MODULE_AUTOLOAD") or "").split():
            d.appendVar("KERNEL_MODULE_AUTOLOAD", " ipv6")
}

oea_ipv6_privacy_config() {
	config="${B}/.config"
	kconfig="${S}/net/ipv6/Kconfig"
	[ -f "$config" ] || bbfatal "IPv6 privacy: kernel configuration not found: $config"
	[ -f "$kconfig" ] || bbfatal "IPv6 privacy: IPv6 Kconfig not found: $kconfig"
	grep -Eq '^CONFIG_IPV6=(y|m)$' "$config" ||
		bbfatal "IPv6 privacy: CONFIG_IPV6 must be enabled"

	if grep -Eq '^[[:space:]]*(menu)?config[[:space:]]+IPV6_PRIVACY([[:space:]]|$)' "$kconfig"; then
		sed -i \
			-e '/^CONFIG_IPV6_PRIVACY=/d' \
			-e '/^# CONFIG_IPV6_PRIVACY is not set/d' \
			"$config"
		echo "CONFIG_IPV6_PRIVACY=y" >> "$config"

		if [ "${S}" = "${B}" ]; then
			yes '' | oe_runmake oldconfig
		else
			yes '' | oe_runmake -C "${S}" O="${B}" oldconfig
		fi

		grep -qx "CONFIG_IPV6_PRIVACY=y" "$config" ||
			bbfatal "IPv6 privacy: kernel rejected CONFIG_IPV6_PRIVACY=y"
	else
		bbnote "IPv6 privacy: kernel has unconditional privacy extension support"
	fi
}
