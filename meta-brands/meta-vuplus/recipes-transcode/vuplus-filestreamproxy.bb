SUMMARY = "file transcoding util."
PRIORITY = "required"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

inherit autotools gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r4"

SRC_URI = "git://code.vuplus.com/git/filestreamproxy.git;protocol=http;branch=master"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${S}/src/filestreamproxy ${D}/usr/bin
}

pkg_prerm_${PN}() {
#!/bin/sh
grep -vE '^#*\s*8003' $D/etc/inetd.conf > $D/tmp/inetd.tmp
mv $D/tmp/inetd.tmp $D/etc/inetd.conf

if [ -z "$D" -a -f "/etc/init.d/inetd.busybox" ]; then
	/etc/init.d/inetd.busybox restart
fi
}

pkg_preinst_${PN}() {
#!/bin/sh
grep -vE '^#*\s*8003' $D/etc/inetd.conf > $D/tmp/inetd.tmp
mv $D/tmp/inetd.tmp $D/etc/inetd.conf

if [ -z "$D" -a -f "/etc/init.d/inetd.busybox" ]; then
	/etc/init.d/inetd.busybox restart
fi
}

pkg_postinst_${PN}() {
#!/bin/sh
if ! grep -qE '^#*\s*8003' $D/etc/inetd.conf; then
        echo -e "8003\t\tstream\ttcp6\tnowait\troot\t/usr/bin/filestreamproxy\tfilestreamproxy" >> $D/etc/inetd.conf
fi
if [ -z "$D" -a -f "/etc/init.d/inetd.busybox" ]; then
	/etc/init.d/inetd.busybox restart
fi
}
