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

SRC_URI[md5sum] = "63aea7fafafb5dcebc097c6612a183ba"
SRC_URI[sha256sum] = "9d0753fbfb5930eeef047e3afcf3ff9fc6fe42e3284c33a31966ee11e421583b"
