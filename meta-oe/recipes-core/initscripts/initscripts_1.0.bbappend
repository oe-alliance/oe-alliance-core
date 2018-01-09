FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

CONFFILES_${PN}_remove = "${sysconfdir}/init.d/checkroot.sh"

# Build initscript-functions as dummy package only as shitquake refuses to fulfill the RDEPENDS for it.
FILES_${PN}-functions = ""
ALLOW_EMPTY_${PN}-functions = "1"

# Try to convince shitquake to fulfill the dependency, but now it's non-fatal if shitquake doesn't
RDEPENDS_${PN} += "sdparm initd-functions initscripts-functions"

DEPENDS_append_class-target = " sysvinit update-rc.d insserv"
PACKAGE_WRITE_DEPS_append = " ${@bb.utils.contains('DISTRO_FEATURES','systemd','systemd-systemctl-native','',d)}"
PACKAGE_WRITE_DEPS_append = " sysvinit update-rc.d insserv"

SRC_URI += "file://hotplug.sh \
            file://procps \
            file://fastrestore_openatv.sh \
            file://skeleton \
            file://halt.default \
            file://tmpfs.default \
            file://umountroot \
"

MASKED_SCRIPTS += " \
            procps \
            hotplug \
            mountkernfs \
"

inherit insserv

INITSCRIPT_NAMES_${PN} = "${@bb.utils.contains('TARGET_ARCH','arm','alignment.sh','',d)} \
banner.sh mountkernfs.sh procps mountall.sh checkroot.sh devpts.sh hotplug.sh read-only-rootfs-hook.sh \
populate-volatile.sh dmesg.sh urandom hostname.sh bootmisc.sh \
${@bb.utils.contains('DISTRO','openatv','fastrestore','',d)} \
sendsigs umountnfs.sh umountfs umountroot halt reboot mountnfs.sh rmnologin.sh"

do_install() {
#
# Create directories and install device independent scripts
#
	install -d ${D}${sysconfdir}/default
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${sysconfdir}/rcS.d
	install -d ${D}${sysconfdir}/rc0.d
	install -d ${D}${sysconfdir}/rc1.d
	install -d ${D}${sysconfdir}/rc2.d
	install -d ${D}${sysconfdir}/rc3.d
	install -d ${D}${sysconfdir}/rc4.d
	install -d ${D}${sysconfdir}/rc5.d
	install -d ${D}${sysconfdir}/rc6.d
	install -d ${D}${sysconfdir}/default
	install -d ${D}${sysconfdir}/default/volatiles
	# Holds state information pertaining to urandom
	install -d ${D}${localstatedir}/lib/urandom

	install -m 0644    ${WORKDIR}/halt.default	${D}${sysconfdir}/default/halt
	install -m 0644    ${WORKDIR}/tmpfs.default	${D}${sysconfdir}/default/tmpfs
	install -m 0755    ${WORKDIR}/skeleton		${D}${sysconfdir}/init.d
	install -m 0644    ${WORKDIR}/functions		${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/bootmisc.sh	${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/checkroot.sh	${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/halt		${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/hostname.sh	${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/mountall.sh	${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/mountnfs.sh	${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/reboot		${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/rmnologin.sh	${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/sendsigs		${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/single		${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/umountnfs.sh	${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/umountroot	${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/urandom		${D}${sysconfdir}/init.d
	sed -i ${D}${sysconfdir}/init.d/urandom -e 's,/var/,${localstatedir}/,g;s,/etc/,${sysconfdir}/,g'
	install -m 0755    ${WORKDIR}/devpts.sh		${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/procps		${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/devpts		${D}${sysconfdir}/default
	install -m 0755    ${WORKDIR}/sysfs.sh		${D}${sysconfdir}/init.d/mountkernfs.sh
	install -m 0755    ${WORKDIR}/populate-volatile.sh ${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/read-only-rootfs-hook.sh ${D}${sysconfdir}/init.d
	install -m 0644    ${WORKDIR}/volatiles		${D}${sysconfdir}/default/volatiles/00_core
	if [ ${@ oe.types.boolean('${VOLATILE_LOG_DIR}') } = True ]; then
		echo "l root root 0755 /var/log /var/volatile/log" >> ${D}${sysconfdir}/default/volatiles/00_core
	fi
	install -m 0755    ${WORKDIR}/dmesg.sh		${D}${sysconfdir}/init.d
	install -m 0644    ${WORKDIR}/logrotate-dmesg.conf ${D}${sysconfdir}/

	if [ "${TARGET_ARCH}" = "arm" ]; then
		install -m 0755 ${WORKDIR}/alignment.sh	${D}${sysconfdir}/init.d
	fi

	if ${@bb.utils.contains('DISTRO_FEATURES','selinux','true','false',d)}; then
		install -d ${D}/${base_sbindir}
		install -m 0755 ${WORKDIR}/sushell ${D}/${base_sbindir}
	fi
#
# Install device dependent scripts
#
	install -m 0755 ${WORKDIR}/banner.sh	${D}${sysconfdir}/init.d/banner.sh
	install -m 0755 ${WORKDIR}/umountfs	${D}${sysconfdir}/init.d/umountfs


	install -m 0755    ${WORKDIR}/hotplug.sh    ${D}${sysconfdir}/init.d

	perl -i -pe 's:mount -a.+?$:mount -a -t nonfs,nfs4,smbfs,cifs,ncp,ncpfs,coda,ocfs2,gfs,gfs2,ceph -O no_netdev 2>/dev/null:' ${D}${sysconfdir}/init.d/mountall.sh
	perl -i -pe 's:(mount -a).*?$:$1:' ${D}${sysconfdir}/init.d/mountnfs.sh

	if [ "x${DISTRO}" = "xopenatv" ]; then
		install -m 0755    ${WORKDIR}/fastrestore_openatv.sh	${D}${sysconfdir}/init.d/fastrestore
	fi

	# run bootmisc.sh after S37populate-volatile.sh  to fix /tmp issue
	update-rc.d -f -r ${D} bootmisc.sh remove
	update-rc.d -r ${D} bootmisc.sh start 55 S .

}
