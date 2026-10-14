# Supply legacy joystick access and the DragonRise USB HID driver on machines
# which opt into RetroGaming. Keep built-in support and existing rumble policy.
# Run after the vendor configuration so every kernel provider is covered.

# machine_kernel_pr assigns PR in an anonymous function, so extend its input.
MACHINE_KERNEL_PR:append = "${@'.rginput1' if 'virtual/kernel' in (d.getVar('PROVIDES') or '').split() else ''}"

python __anonymous() {
    if "virtual/kernel" in (d.getVar("PROVIDES") or "").split():
        d.appendVarFlag("do_configure", "postfuncs", " retrogaming_input_config")
}

retrogaming_input_config() {
	config="${B}/.config"
	[ -f "$config" ] || bbfatal "RetroGaming input: kernel configuration not found: $config"
	changed=no
	expectedSettings=""

	for symbol in INPUT_JOYDEV HID_DRAGONRISE; do
		expected=$(grep -E "^CONFIG_$symbol=(y|m)$" "$config" || true)
		if [ -z "$expected" ]; then
			sed -i -e "/^CONFIG_$symbol=/d" -e "/^# CONFIG_$symbol is not set/d" "$config"
			expected="CONFIG_$symbol=m"
			echo "$expected" >> "$config"
			changed=yes
		fi
		expectedSettings="$expectedSettings $expected"
	done

	if [ "$changed" = yes ]; then
		if [ "${S}" = "${B}" ]; then
			yes '' | oe_runmake oldconfig
		else
			yes '' | oe_runmake -C "${S}" O="${B}" oldconfig
		fi
	fi

	for expected in $expectedSettings; do
		grep -qxF "$expected" "$config" ||
			bbfatal "RetroGaming input: kernel rejected $expected; check input/HID dependencies"
	done
}
