SRCDATE = "20230405_r1"

require chromium-browser.inc

COMPATIBLE_MACHINE = "^(vuduo4klite)$"

SRC_URI += "file://0001-select-vuduo4klite-ir-keymap.patch \
            file://irkeymap_8052.xml \
            file://vu-ir-ok-adapter.py \
"

do_install:append() {
	install -m 0644 ${UNPACKDIR}/irkeymap_8052.xml ${D}/usr/local/chromium/bin/irkeymap_8052.xml
	install -m 0755 ${UNPACKDIR}/vu-ir-ok-adapter.py ${D}/usr/local/chromium/bin/vu-ir-ok-adapter.py
}

SRC_URI[md5sum] = "0779f182e7eea90ad7051a571a942d3d"
SRC_URI[sha256sum] = "4b11482b4a0641994b07f107368eb53bbd0e6fce008e5fb19158b389c3132086"
