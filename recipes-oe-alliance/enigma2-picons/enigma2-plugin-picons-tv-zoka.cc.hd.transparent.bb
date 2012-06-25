DESCRIPTION = "Zonk.cc HD Picons (transparent)"

PICONS_FILENAME = "picons-hd-transparent_${SRCDATE}"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/picons-hd-transparent*", d))
	except:
		pass
}

include enigma2-plugin-picons-zoka.cc.inc

SRC_URI[md5sum] = "7d0799a7973224234810acc51c8ec1da"
SRC_URI[sha256sum] = "e42c3190b5d1123be9f32f1750142c3f664ece6b1c3492d29173faa48168ab6e"
