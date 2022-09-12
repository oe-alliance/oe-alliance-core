FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://udev-builtin-input_id.patch"

do_install:append() {
	install -d ${D}${base_libdir}
	ln -sf libudev.so.1.6.3  ${D}${base_libdir}/libudev.so.0
}
