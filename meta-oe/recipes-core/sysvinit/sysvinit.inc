#
# Base recipe: meta/recipes-core/sysvinit/sysvinit_2.88dsf.bb
# Base branch: daisy
#

SUMMARY = "System-V like init"
DESCRIPTION = "This package is required to boot in most configurations.  \
It provides the /sbin/init program.  This is the first process started on boot, \
and the last process terminated before the system halts."
HOMEPAGE = "http://savannah.nongnu.org/projects/sysvinit/"

INC_PR = "r0"

inherit debian-package
PV = "2.88dsf"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe \
                    file://COPYRIGHT;endline=15;md5=349c872e0066155e1818b786938876a4"

# install.patch: patch file to fix hard code in Makefile.
# crypt-lib.patch: patch file to check for $LCRYPT in the environment, not in the host

SRC_URI += "file://install.patch \
            file://crypt-lib_debian.patch"

CFLAGS_prepend = "-D_GNU_SOURCE "
export LCRYPT = "-lcrypt"
EXTRA_OEMAKE += "'base_bindir=${base_bindir}' \
		 'base_sbindir=${base_sbindir}' \
		 'bindir=${bindir}' \
		 'sbindir=${sbindir}' \
		 'sysconfdir=${sysconfdir}' \
		 'includedir=${includedir}' \
		 'mandir=${mandir}'"

EXTRA_OEMAKE += "DISTRO=Debian"

# Install files follow debian/rules
do_install () {
	oe_runmake 'ROOT=${D}' install
	for i in bootlogd initscripts sysv-rc; do
		oe_runmake -C ${S}/debian/src/$i install DESTDIR=${D}
	done
	
	# Some files belong to $base_libdir
	mv ${D}${sbindir}/fsck.nfs ${D}${base_sbindir}/fsck.nfs
	
	# initscripts package may include /sys
	install -d  ${D}/sys
	
	# Install files for sysvinit-utils package follow debian/rules
	if [ ! -d ${D}${base_libdir}/init ]; then
		install -d ${D}${base_libdir}/init
	fi
	install ${S}/debian/init-d-script ${D}${base_libdir}/init
	install ${S}/debian/service/service ${D}${sbindir}/
		
	install -d ${D}${base_libdir}/${DPN}
	install -m 0755 ${D}${base_sbindir}/init ${D}${base_libdir}/${DPN}
	ln -sf init ${D}${base_libdir}/${DPN}/telinit

	install -d ${D}${datadir}/${DPN}
	install -m 0644 ${S}/debian/share/inittab ${D}${datadir}/${DPN}/inittab
	install -m 0755 ${S}/debian/share/update-rc.d ${D}${datadir}/${DPN}/
	rm -rf ${D}${sysconfdir}/hurd
}
