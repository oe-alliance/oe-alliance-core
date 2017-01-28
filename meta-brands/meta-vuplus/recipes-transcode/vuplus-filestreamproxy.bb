SUMMARY = "file transcoding util."
PRIORITY = "required"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

inherit autotools gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
PR = "r4"

PROVIDES += "virtual/filestreamproxy"
RPROVIDES_${PN} += "virtual/filestreamproxy"
RDEPENDS_${PN} = "busybox-inetd"

SRC_URI = "git://code.vuplus.com/git/filestreamproxy.git;protocol=http;branch=master"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${S}/src/filestreamproxy ${D}/usr/bin
}

pkg_prerm_${PN}() {
#!/bin/sh
grep -vE '^#*\s*8003' /etc/inetd.conf > /tmp/inetd.tmp
mv /tmp/inetd.tmp /etc/inetd.conf

/etc/init.d/inetd.busybox restart
}

pkg_preinst_${PN}() {
#!/bin/sh
grep -vE '^#*\s*8003' /etc/inetd.conf > /tmp/inetd.tmp
mv /tmp/inetd.tmp /etc/inetd.conf

/etc/init.d/inetd.busybox restart
}

pkg_postinst_${PN}() {
#!/bin/sh
if ! grep -qE '^#*\s*8003' /etc/inetd.conf; then
        echo -e "8003\t\tstream\ttcp6\tnowait\troot\t/usr/bin/filestreamproxy\tfilestreamproxy" >> /etc/inetd.conf
	/etc/init.d/inetd.busybox restart
fi
}
