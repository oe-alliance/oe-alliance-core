DESCRIPTION = "udev is a daemon which dynamically creates and removes device nodes from \
/dev/, handles hotplug events and loads drivers at boot time. It replaces \
the hotplug package and requires a kernel not older than 2.6.12."
RPROVIDES_${PN} = "hotplug"

require udev_124.inc

PR = "${INC_PR}.5"

LD = "${CC}"

SRC_URI += "file://noasmlinkage.patch \
	    file://flags.patch \
	    file://vol_id_ld.patch \
	    file://udevtrigger_add_devname_filtering.patch \
	    file://libvolume-id-soname.patch \
	    file://mtd-exclude-persistent.patch \
	    file://mount.blacklist \
	    file://run.rules \
	    file://default \
	    file://local.rules \
	    file://41-od-linux-2.6.18-misc.rules \
	   "

FILES_${PN} += "${base_libdir}/udev/*"
FILES_${PN}-dbg += "${base_libdir}/udev/.debug"
UDEV_EXTRAS = "extras/firmware/ extras/scsi_id/ extras/volume_id/"
EXTRA_OEMAKE += "libudevdir=/lib/udev libdir=${base_libdir} prefix="

do_install () {
	install -d ${D}${usrsbindir} \
		   ${D}${sbindir}
	oe_runmake 'DESTDIR=${D}' INSTALL=install install
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/udev

	install -d ${D}${sysconfdir}/default
	install -m 0755 ${WORKDIR}/default ${D}${sysconfdir}/default/udev

	install -d ${D}${sysconfdir}/udev/rules.d/

	install -m 0644 ${WORKDIR}/mount.blacklist     ${D}${sysconfdir}/udev/
	install -m 0644 ${WORKDIR}/local.rules         ${D}${sysconfdir}/udev/rules.d/local.rules
	install -m 0644 ${WORKDIR}/permissions.rules   ${D}${sysconfdir}/udev/rules.d/permissions.rules
	install -m 0644 ${WORKDIR}/run.rules          ${D}${sysconfdir}/udev/rules.d/run.rules
	install -m 0644 ${WORKDIR}/udev.rules          ${D}${sysconfdir}/udev/rules.d/udev.rules
	install -m 0644 ${WORKDIR}/links.conf          ${D}${sysconfdir}/udev/links.conf
	if [ "${UDEV_DEVFS_RULES}" = "1" ]; then
		install -m 0644 ${WORKDIR}/devfs-udev.rules ${D}${sysconfdir}/udev/rules.d/devfs-udev.rules
	fi

	touch ${D}${sysconfdir}/udev/saved.uname
	touch ${D}${sysconfdir}/udev/saved.cmdline
	touch ${D}${sysconfdir}/udev/saved.atags

	install -d ${D}${sysconfdir}/udev/scripts/

	install -m 0755 ${WORKDIR}/mount.sh ${D}${sysconfdir}/udev/scripts/mount.sh
	install -m 0755 ${WORKDIR}/network.sh ${D}${sysconfdir}/udev/scripts

	install -d ${D}${base_libdir}/udev/

	install -m 0644 ${WORKDIR}/41-od-linux-2.6.18-misc.rules ${D}${sysconfdir}/udev/rules.d

	# disable automatic mounts
	sed -e 's,^\(.*/mount\.sh.*\)$,#\1,' -i ${D}${sysconfdir}/udev/rules.d/local.rules
}

SRC_URI[md5sum] = "2ea9229208154229c5d6df6222f74ad7"
SRC_URI[sha256sum] = "cc9f58ff58fbd3f5868e1f1e368e3c93e1f441afd0ac1dcbd5d01a9ce5b5b0d7"
