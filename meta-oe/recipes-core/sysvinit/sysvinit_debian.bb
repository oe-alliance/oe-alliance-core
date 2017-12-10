#
# Base recipe: meta/recipes-core/sysvinit/sysvinit_2.88dsf.bb
# Base branch: daisy
#

require sysvinit.inc

PR = "${INC_PR}.1"

do_install_append() {
	mv ${D}${base_bindir}/pidof ${D}${base_bindir}/pidof.${DPN}

	# mountpoint is moved to sysvinit-mountpoint package
	rm -f ${D}${base_bindir}/mountpoint ${D}${mandir}/man1/mountpoint.1
}

PACKAGES =+ "sysv-rc bootlogd ${PN}-core  \
             ${PN}-utils ${PN}-initscripts"

FILES_${PN} += " \
		${base_libdir}/${PN}/*"
FILES_${PN}-dbg += " \
		${base_libdir}/${PN}/.debug"
FILES_sysv-rc += " \
		${sbindir}/invoke-rc.d \
		${sbindir}/update-rc.d \
		${sysconfdir}/init.d/README \
		${sysconfdir}/init.d/rc \
		${sysconfdir}/init.d/rcS \
		${sysconfdir}/rc*.d \
		${datadir}/sysv-rc"
FILES_bootlogd += " \
		${base_sbindir}/bootlogd \
		${sysconfdir}/init.d/bootlogd \
		${sysconfdir}/init.d/stop*"
# mountpoint is moved to sysvinit-mountpoint package
RDEPENDS_${PN}-initscripts += "${PN}-mountpoint"
FILES_${PN}-initscripts += " \
		${sysconfdir}/default/* \
		${sysconfdir}/init.d/* \
		${sysconfdir}/network \
		${base_libdir}/init/* \
		run sys \
		${base_sbindir}/fsck.nfs \
		${localstatedir}${base_libdir}/initscripts \
		${localstatedir}${base_libdir}/urandom \
		${localstatedir}/log/fsck"
FILES_${PN}-core += " \
		${base_sbindir}/halt* \
		${base_sbindir}/init* \
		${base_sbindir}/poweroff* \
		${base_sbindir}/reboot* \
		${base_sbindir}/runlevel* \
		${base_sbindir}/shutdown* \
		${base_sbindir}/telinit* \
		${datadir}/${DPN} \
		${includedir}"
FILES_${PN}-utils += " \
		${base_bindir}/pidof* \
		${base_libdir}/init/init-d-script \
		${base_sbindir}/fstab-decode \
		${base_sbindir}/killall5* \
		${base_sbindir}/sulogin* \
		${bindir}/last \
		${bindir}/lastb \
		${bindir}/mesg \
		${sbindir}/service"

# Add update-alternatives definitions
inherit update-alternatives

ALTERNATIVE_PRIORITY="200"
ALTERNATIVE_${PN}-core = "halt init poweroff reboot runlevel"
ALTERNATIVE_${PN}-utils = "pidof killall5 sulogin"

ALTERNATIVE_LINK_NAME[halt] = "${base_sbindir}/halt"
ALTERNATIVE_LINK_NAME[init] = "${base_sbindir}/init"
ALTERNATIVE_LINK_NAME[poweroff] = "${base_sbindir}/poweroff"
ALTERNATIVE_LINK_NAME[reboot] = "${base_sbindir}/reboot"
ALTERNATIVE_LINK_NAME[runlevel] = "${base_sbindir}/runlevel"
ALTERNATIVE_LINK_NAME[pidof] = "${base_bindir}/pidof"
ALTERNATIVE_LINK_NAME[killall5] = "${base_sbindir}/killall5"
ALTERNATIVE_LINK_NAME[sulogin] = "${base_sbindir}/sulogin"

inherit insserv
INITSCRIPT_PACKAGES = "bootlogd ${PN}-initscripts"
INITSCRIPT_NAMES_bootlogd = "bootlogd stop-bootlogd-single stop-bootlogd"
INITSCRIPT_NAMES_${PN}-initscripts = " \
    mountkernfs.sh hostname.sh mountdevsubfs.sh \
    checkroot.sh checkroot-bootclean.sh checkfs.sh \
    mountall.sh mountall-bootclean.sh bootmisc.sh urandom \
"

pkg_postinst_bootlogd() {
set -e
#
# Create initial log file
#

for F in $D${localstatedir}/log/boot
do
	if [ ! -f "$F" ] && touch "$F" >/dev/null 2>&1
	then
		echo "(Nothing has been logged yet.)" >| "$F"
		chown root:adm "$F"
		chmod 640 "$F"
	fi
done
}

pkg_postrm_bootlogd() {
set -e

case "$1" in
    purge)
	#
	# Remove log files
	#
	rm -f $D${localstatedir}/log/boot
	;;
esac
}
