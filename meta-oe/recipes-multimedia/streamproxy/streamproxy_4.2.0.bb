SUMMARY = "streamproxy manages streaming data to a PC using enigma2"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"
SRCREV = "d9396c07f1ddfcbacec70350604fea0d3ccae821"

inherit autotools gitpkgv

S = "${WORKDIR}/git"

VERSION := "${PV}"
PV = "${VERSION}+git${SRCPV}"
PKGV = "${VERSION}+git${GITPKGV}"

SRC_URI="git://github.com/oe-mirrors/streamproxy.git;protocol=https"

pkg_postinst_${PN}() {
#!/bin/sh
LINE='8001\t\tstream\ttcp6\tnowait\troot\t/usr/bin/streamproxy\t\tstreamproxy'

if grep -q '^8001' $D/etc/inetd.conf; then
	# Remove old entries for port 8001 (user fixes or previous installs)
	grep -v '^[#\s]*8001' $D/etc/inetd.conf > $D/etc/inetd.tmp
	mv $D/etc/inetd.tmp $D/etc/inetd.conf
fi

if grep -q '^8002' $D/etc/inetd.conf; then
	# Add before port 8002 if it exists
	sed -i "s#^[#\s]*8002#${LINE}\n8002#" $D/etc/inetd.conf
else
	# Just append
	echo -e "${LINE}" >> $D/etc/inetd.conf
fi

if [ -z "$D" -a -f "/etc/init.d/inetd.busybox" ]; then
	/etc/init.d/inetd.busybox restart
fi
}

pkg_prerm_${PN}() {
#!/bin/sh
if grep -q '^[#\s]*8001' $D/etc/inetd.conf; then
	grep -v '^[#\s]*8001' $D/etc/inetd.conf > $D/etc/inetd.tmp
	mv $D/etc/inetd.tmp $D/etc/inetd.conf
fi

if [ -z "$D" -a -f "/etc/init.d/inetd.busybox" ]; then
	/etc/init.d/inetd.busybox restart
fi
}

