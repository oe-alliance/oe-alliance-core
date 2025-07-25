FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR .= ".1"

SRC_URI += "file://udev-builtin-input_id.patch \
	file://init \
	file://60-ssd-scheduler.rules \
"

do_install:append() {
	install -d ${D}${base_libdir}
	ln -sf libudev.so.1 ${D}${base_libdir}/libudev.so.0
	install -m 0644 ${UNPACKDIR}/60-ssd-scheduler.rules ${D}${sysconfdir}/udev/rules.d/60-ssd-scheduler.rules
}

INITSCRIPT_PARAMS = "start 05 S ."

DEPENDS += " udev-extraconf"
RDEPENDS:${PN} += " udev-extraconf"
