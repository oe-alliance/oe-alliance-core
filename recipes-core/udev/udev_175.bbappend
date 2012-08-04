PRINC = "1"

SRC_URI += " \
	file://init \
"

inherit update-rc.d

INITSCRIPT_NAME = "udev"
INITSCRIPT_PARAMS = "start 03 S ."

do_install_append () {
	rm ${D}${sysconfdir}/udev/rules.d/*.rules || /bin/true
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/udev
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"
