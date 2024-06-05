#
# For now, we will skip building of a gcc package if it is a uclibc one
# and our build is not a uclibc one, and we skip a glibc one if our build
# is a uclibc build.
#
# See the note in gcc/gcc_3.4.0.oe
#

python __anonymous () {
    import bb, re
    uc_os = (re.match('.*uclibc*', d.getVar('TARGET_OS')) != None)
    if uc_os:
        raise bb.parse.SkipPackage("incompatible with target %s" %
                                   d.getVar('TARGET_OS'))
}

INHIBIT_SYSROOT_STRIP = "1"

PACKAGES = "${PN}-dbg catchsegv sln nscd ldconfig ldd tzcode glibc-thread-db ${PN}-pic libcidn libmemusage libsegfault ${PN}-pcprofile libsotruss ${PN} ${PN}-utils glibc-extra-nss ${PN}-dev ${PN}-staticdev ${PN}-doc"

# The ld.so in this glibc supports the GNU_HASH
RPROVIDES:${PN} = "eglibc rtld(GNU_HASH)"
RPROVIDES:${PN}-utils = "eglibc-utils"
RPROVIDES:${PN}-mtrace = "eglibc-mtrace libc-mtrace"
RPROVIDES:${PN}-pic = "eglibc-pic"
RPROVIDES:${PN}-dev = "eglibc-dev libc6-dev virtual-libc-dev"
RPROVIDES:${PN}-staticdev = "eglibc-staticdev"
RPROVIDES:${PN}-doc = "eglibc-doc"
RPROVIDES:glibc-extra-nss = "eglibc-extra-nss"
RPROVIDES:glibc-thread-db = "eglibc-thread-db"
RPROVIDES:${PN}-pcprofile = "eglibc-pcprofile"
RPROVIDES:${PN}-dbg = "eglibc-dbg"
libc_baselibs = "${base_libdir}/libcrypt*.so.* ${base_libdir}/libcrypt-*.so ${base_libdir}/libc.so.* ${base_libdir}/libc-*.so ${base_libdir}/libm*.so.* ${base_libdir}/libm-*.so ${base_libdir}/libmvec-*.so ${base_libdir}/ld*.so.* ${base_libdir}/ld-*.so ${base_libdir}/libpthread*.so.* ${base_libdir}/libpthread-*.so ${base_libdir}/libresolv*.so.* ${base_libdir}/libresolv-*.so ${base_libdir}/librt*.so.* ${base_libdir}/librt-*.so ${base_libdir}/libutil*.so.* ${base_libdir}/libutil-*.so ${base_libdir}/libnsl*.so.* ${base_libdir}/libnsl-*.so ${base_libdir}/libnss_files*.so.* ${base_libdir}/libnss_files-*.so ${base_libdir}/libnss_compat*.so.* ${base_libdir}/libnss_compat-*.so ${base_libdir}/libnss_dns*.so.* ${base_libdir}/libnss_dns-*.so ${base_libdir}/libdl*.so.* ${base_libdir}/libdl-*.so ${base_libdir}/libanl*.so.* ${base_libdir}/libanl-*.so ${base_libdir}/libBrokenLocale*.so.* ${base_libdir}/libBrokenLocale-*.so"
libc_baselibs:append:aarch64 = " /lib/ld-linux-aarch64*.so.1"
INSANE_SKIP:${PN}:append:aarch64 = " libdir"

FILES:${PN} = "${libc_baselibs} ${libexecdir}/*"
RRECOMMENDS:${PN} = "${@bb.utils.filter('DISTRO_FEATURES', 'ldconfig', d)}"
FILES:ldconfig = "${base_sbindir}/ldconfig ${sysconfdir}/ld.so.conf"
FILES:ldd = "${bindir}/ldd"
FILES:libsegfault = "${base_libdir}/libSegFault*"
FILES:libcidn = "${base_libdir}/libcidn-*.so ${base_libdir}/libcidn.so.*"
FILES:libmemusage = "${base_libdir}/libmemusage.so"
FILES:glibc-extra-nss = "${base_libdir}/libnss_*-*.so ${base_libdir}/libnss_*.so.*"
FILES:sln = "${base_sbindir}/sln"
FILES:${PN}-pic = "${libdir}/*_pic.a ${libdir}/*_pic.map ${libdir}/libc_pic/*.o"
FILES:libsotruss = "${libdir}/audit/sotruss-lib.so"
FILES_SOLIBSDEV = "${libdir}/lib*${SOLIBSDEV}"
FILES:${PN}-dev += "${libdir}/*_nonshared.a ${base_libdir}/*_nonshared.a ${base_libdir}/*.o ${datadir}/aclocal"
RDEPENDS:${PN}-dev = "linux-libc-headers-dev"
FILES:${PN}-staticdev += "${libdir}/*.a ${base_libdir}/*.a"
FILES:nscd = "${sbindir}/nscd* ${sysconfdir}/init.d/nscd ${systemd_unitdir}/system/nscd* ${sysconfdir}/tmpfiles.d/nscd.conf \
              ${sysconfdir}/nscd.conf ${sysconfdir}/default/volatiles/98_nscd ${localstatedir}/db/nscd"
FILES:${PN}-mtrace = "${bindir}/mtrace"
FILES:tzcode = "${bindir}/tzselect ${sbindir}/zic ${sbindir}/zdump"
FILES:${PN}-utils = "${bindir}/* ${sbindir}/*"
FILES:catchsegv = "${bindir}/catchsegv"
RDEPENDS:catchsegv = "libsegfault"
FILES:${PN}-pcprofile = "${base_libdir}/libpcprofile.so"
FILES:glibc-thread-db = "${base_libdir}/libthread_db.so.* ${base_libdir}/libthread_db-*.so"
RPROVIDES:${PN}-dev += "libc-dev"
RPROVIDES:${PN}-staticdev += "libc-staticdev"

SUMMARY:sln = "The static ln"
DESCRIPTION:sln = "Similar to the 'ln' utility, but statically linked.  sln is useful to make symbolic links to dynamic libraries if the dynamic linking system, for some reason, is not functional."
SUMMARY:nscd = "Name service cache daemon"
DESCRIPTION:nscd = "nscd, name service cache daemon, caches name service lookups for the passwd, group and hosts information.  It can damatically improvide performance with remote, such as NIS or NIS+, name services."
SUMMARY:glibc-extra-nss = "hesiod, NIS and NIS+ nss libraries"
DESCRIPTION:glibc-extra-nss = "glibc: nis, nisplus and hesiod search services."
SUMMARY:ldd = "print shared library dependencies"
DESCRIPTION:ldd = "${bindir}/ldd prints shared library dependencies for each program or shared library specified on the command line."
SUMMARY:${PN}-utils = "Miscellaneous utilities provided by glibc"
DESCRIPTION:${PN}-utils = "Miscellaneous utilities including getconf, iconv, locale, gencat, ..."
DESCRIPTION:libsotruss = "Library to support sotruss which traces calls through PLTs"
DESCRIPTION:tzcode = "tzcode, timezone zoneinfo utils -- zic, zdump, tzselect"

inherit multilib_header

do_install() {
	oe_runmake install_root=${D} install
	install -Dm 0644 ${S}/etc/ld.so.conf ${D}/${sysconfdir}/ld.so.conf
	install -d ${D}${localedir}
	make -f ${WORKDIR}/generate-supported.mk IN="${S}/localedata/SUPPORTED" OUT="${WORKDIR}/SUPPORTED"
	# get rid of some broken files...
	for i in ${GLIBC_BROKEN_LOCALES}; do
		sed -i "/$i/d" ${WORKDIR}/SUPPORTED
	done
	rm -f ${D}${sysconfdir}/rpc
	rm -rf ${D}${datadir}/zoneinfo
	rm -rf ${D}${libexecdir}/getconf

	rm -f ${D}${sysconfdir}/localtime

	# remove empty glibc dir
	if [ -d ${D}${libexecdir} ]; then
		rmdir --ignore-fail-on-non-empty ${D}${libexecdir}
	fi

	oe_multilib_header bits/syscall.h

	if [ -f ${D}${bindir}/mtrace ]; then
		sed -i -e '1s,#!.*perl,#! ${USRBINPATH}/env perl,' -e '2s,exec.*perl,exec ${USRBINPATH}/env perl,' ${D}${bindir}/mtrace
	fi
	rm -rf ${D}${includedir}/rpcsvc/rquota*
	# Info dir listing isn't interesting at this point so remove it if it exists.
	if [ -e "${D}${infodir}/dir" ]; then
		rm -f ${D}${infodir}/dir
	fi

	if echo ${PN}|grep -q "glibc-initial"; then
		return
	fi

	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${localstatedir}/db/nscd
	install -m 0755 ${UNPACKDIR}/nscd/nscd.init ${D}${sysconfdir}/init.d/nscd
	install -m 0755 ${UNPACKDIR}/nscd/nscd.conf ${D}${sysconfdir}/nscd.conf
	sed -i "s%daemon%start-stop-daemon --start --exec%g" ${D}${sysconfdir}/init.d/nscd

	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${UNPACKDIR}/nscd/nscd.service ${D}${systemd_unitdir}/system/

	if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
		install -d ${D}${sysconfdir}/tmpfiles.d
		echo "d /run/nscd 755 root root -" \
			> ${D}${sysconfdir}/tmpfiles.d/nscd.conf
	else
		install -d ${D}${sysconfdir}/default/volatiles
		echo "d root root 0755 /var/run/nscd none" \
			> ${D}${sysconfdir}/default/volatiles/98_nscd
	fi
}

def get_libc_fpu_setting(bb, d):
    if d.getVar('TARGET_FPU') in [ 'soft', 'ppc-efd' ]:
        return "--without-fp"
    return ""

do_install:append:aarch64 () {
        if [ "${base_libdir}" != "${nonarch_base_libdir}" ]; then
                # The aarch64 ABI says the dynamic linker -must- be /lib/ld-linux-aarch64[_be].so.1
                install -d ${D}${nonarch_base_libdir}
                if [ -e ${D}${base_libdir}/ld-linux-aarch64.so.1 ]; then
                        ln -s ${@oe.path.relative('${nonarch_base_libdir}', '${base_libdir}')}/ld-linux-aarch64.so.1 \
                                ${D}${nonarch_base_libdir}/ld-linux-aarch64.so.1
                elif [ -e ${D}${base_libdir}/ld-linux-aarch64_be.so.1 ]; then
                        ln -s ${@oe.path.relative('${nonarch_base_libdir}', '${base_libdir}')}/ld-linux-aarch64_be.so.1 \
                                ${D}${nonarch_base_libdir}/ld-linux-aarch64_be.so.1
                fi
        fi
}

LOCALESTASH = "${WORKDIR}/stashed-locale"
bashscripts = "mtrace sotruss xtrace"

do_stash_locale () {
	dest=${LOCALESTASH}
	install -d $dest${base_libdir} $dest${bindir} $dest${libdir} $dest${datadir}
	# Hide away the locale data from the deployment
	if [ -e ${D}${bindir}/localedef ]; then
		cp -a ${D}${bindir}/localedef $dest${bindir}
	fi
	if [ -e ${D}${libdir}/gconv ]; then
		cp -a ${D}${libdir}/gconv $dest${libdir}
	fi
	if [ -e ${D}${datadir}/i18n ]; then
		cp -a  ${D}${datadir}/i18n $dest${datadir}
	fi

	# Make a copy of all the libraries into the locale stash
	cp -fpPR ${D}${libdir}/* $dest${libdir}
	if [ "${base_libdir}" != "${libdir}" ]; then
		cp -fpPR ${D}${base_libdir}/* $dest${base_libdir}
	fi
	if [ -e ${D}${exec_prefix}/lib ]; then
		if [ ${exec_prefix}/lib != ${base_libdir} ] && [ ${exec_prefix}/lib != ${libdir} ]; then
			cp -fpPR ${D}${exec_prefix}/lib $dest${exec_prefix}
		fi
	fi

	cp -fpPR ${D}${datadir}/* $dest${datadir}
	cp -fpPR ${WORKDIR}/SUPPORTED $dest

	target=$dest/scripts
	mkdir -p $target
	for i in ${bashscripts}; do
		if [ -f ${D}${bindir}/$i ]; then
			cp ${D}${bindir}/$i $target/
		fi
	done
}

addtask do_stash_locale after do_install before do_populate_sysroot do_package
do_stash_locale[dirs] = "${B}"
do_stash_locale[cleandirs] = "${LOCALESTASH}"
SSTATETASKS += "do_stash_locale"
do_stash_locale[sstate-inputdirs] = "${LOCALESTASH}"
do_stash_locale[sstate-outputdirs] = "${COMPONENTS_DIR}/${PACKAGE_ARCH}/glibc-stash-locale"
do_stash_locale[sstate-fixmedir] = "${COMPONENTS_DIR}/${PACKAGE_ARCH}/glibc-stash-locale"

python do_stash_locale_setscene () {
    sstate_setscene(d)
}
addtask do_stash_locale_setscene

PACKAGE_PREPROCESS_FUNCS += "stash_locale_package_cleanup"
SYSROOT_PREPROCESS_FUNCS += "stash_locale_sysroot_cleanup"
stash_locale_cleanup () {
	cleanupdir=$1
	# Remove all files which do_stash_locale() copies
	for i in ${bashscripts}; do
		rm -f $cleanupdir${bindir}/$i
	done
	rm -f $cleanupdir${bindir}/localedef
	rm -rf $cleanupdir${datadir}/i18n
	rm -rf $cleanupdir${libdir}/gconv
	rm -rf $cleanupdir${localedir}
	rm -rf $cleanupdir${datadir}/locale
	rm -rf $cleanupdir${datadir}

	if [ "${libdir}" != "${exec_prefix}/lib" ] && [ "${root_prefix}/lib" != "${exec_prefix}/lib" ]; then
		if [ -d "$cleanupdir${exec_prefix}/lib" ]; then
			if [ -z "${ARCH_DYNAMIC_LOADER}" -o \
			     ! -e "$cleanupdir${exec_prefix}/lib/${ARCH_DYNAMIC_LOADER}" ]; then
				# error out if directory isn't empty
				# this dir should only contain locale dir
				# which has been deleted in the previous step
				rmdir $cleanupdir${exec_prefix}/lib
			fi
		fi
	fi
}

stash_locale_sysroot_cleanup() {
	stash_locale_cleanup ${SYSROOT_DESTDIR}
}
stash_locale_package_cleanup() {
	stash_locale_cleanup ${PKGD}
}

python populate_packages:prepend () {
    if d.getVar('DEBIAN_NAMES'):
        pkgs = d.getVar('PACKAGES').split()
        bpn = d.getVar('BPN')
        prefix = d.getVar('MLPREFIX') or ""
        # Set the base package...
        d.setVar('PKG:' + prefix + bpn, prefix + 'libc6')
        libcprefix = prefix + bpn + '-'
        for p in pkgs:
            # And all the subpackages.
            if p.startswith(libcprefix):
                renamed = p.replace(bpn, 'libc6', 1)
                d.setVar('PKG:' + p, renamed)
        # For backward compatibility with old -dbg package
        d.appendVar('RPROVIDES:' + libcprefix + 'dbg', ' ' + prefix + 'libc-dbg')
        d.appendVar('RCONFLICTS:' + libcprefix + 'dbg', ' ' + prefix + 'libc-dbg')
        d.appendVar('RREPLACES:' + libcprefix + 'dbg', ' ' + prefix + 'libc-dbg')
}

pkg_postinst:nscd () {
	if [ -z "$D" ]; then
		if command -v systemd-tmpfiles >/dev/null; then
			systemd-tmpfiles --create ${sysconfdir}/tmpfiles.d/nscd.conf
		elif [ -e ${sysconfdir}/init.d/populate-volatile.sh ]; then
			${sysconfdir}/init.d/populate-volatile.sh update
		fi
	fi
}
CONFFILES:nscd="${sysconfdir}/nscd.conf"

SYSTEMD_PACKAGES = "nscd"
SYSTEMD_SERVICE:nscd = "nscd.service"
