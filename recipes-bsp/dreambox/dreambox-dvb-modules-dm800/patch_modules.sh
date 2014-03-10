#!/bin/sh

for m in $(find /lib/modules/$(uname -r)/extra -name "*.ko")
	do
	grep -q "gcc-4.6" $m && {
		echo "patching vermagic ${m}"
		sed -i 's/gcc-4.6/gcc-4.8/g' $m || exit 1
	}
	done
	exit 0
}
