DESCRIPTION = "Zonk.cc SD Picons (Black)"

PICONS_FILENAME = "picons-sd-black_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-sd-black*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc

SRC_URI[md5sum] = "5e9bb64137a6d25949567ffc97ac798c"
SRC_URI[sha256sum] = "8674968d0b0cbc17d4ac81d2cd50520b07d80a83461b107d132e6ab59abc0117"
