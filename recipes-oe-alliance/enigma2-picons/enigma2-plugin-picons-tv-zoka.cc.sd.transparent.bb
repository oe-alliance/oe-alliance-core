DESCRIPTION = "Zonk.cc SD Picons (transparent)"

PICONS_FILENAME = "picons-sd-transparent_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-sd-transparent*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc

SRC_URI[md5sum] = "c2f705c8efbc8ae8b19a9ef81bbb1457"
SRC_URI[sha256sum] = "06793594f8a41b7a17145366f82e419c04d56d35f67133135489447870d5812f"
