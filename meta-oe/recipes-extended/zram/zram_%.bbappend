# Get rid of silly dependencies like util-linux
RDEPENDS:${PN} = ""

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
	file://zram.conf \
	file://99-zram.rules \
	file://zram-setup \
"

PR .= ".4"

ZRAM_FACTOR ?= "50"
ZRAM_FACTOR:vusolo = "25"

INHIBIT_UPDATERCD_BBCLASS = "1"

do_install:append() {
	rm -f ${D}${sysconfdir}/init.d/zram

	# Remove systemd related configuration file
	if ${@bb.utils.contains('DISTRO_FEATURES','systemd','false','true',d)}; then
		rm -rf ${D}${systemd_unitdir}/system
	fi

	install -d ${D}${sysconfdir}/modules-load.d
	install -m 0644 ${UNPACKDIR}/zram.conf \
		${D}${sysconfdir}/modules-load.d/zram.conf

	install -d ${D}${nonarch_base_libdir}/udev/rules.d
	install -m 0644 ${UNPACKDIR}/99-zram.rules \
		${D}${nonarch_base_libdir}/udev/rules.d/99-zram.rules

	install -d ${D}${sbindir}
	install -m 0755 ${UNPACKDIR}/zram-setup \
		${D}${sbindir}/zram-setup
	sed -i -e 's/^FACTOR=50$/FACTOR=${ZRAM_FACTOR}/' \
		${D}${sbindir}/zram-setup
}

FILES:${PN}:append = " \
	${sysconfdir}/modules-load.d/zram.conf \
	${nonarch_base_libdir}/udev/rules.d/99-zram.rules \
	${sbindir}/zram-setup \
"

pkg_postinst_ontarget:${PN}:append() {
	update-rc.d -f zram remove >/dev/null 2>&1 || true
	rm -f ${sysconfdir}/init.d/zram

	modprobe zram || true
	udevadm trigger --action=add --sysname-match=zram0 || true
}
