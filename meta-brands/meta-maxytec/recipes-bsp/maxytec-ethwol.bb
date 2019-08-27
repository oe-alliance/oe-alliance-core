SUMMARY = "Activate eth0 wol function"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r0"

SRC_URI = ""

# Generate a mali rules script
do_compile_append () {
	cat > ethwol.sh << EOF
#! /bin/sh
PATH=/sbin:/bin:/usr/sbin:/usr/bin
#activate WakeOnLAN
grep -q 'on' /proc/stb/power/wol
if [ $? -eq 0 ]
then
	echo [WOL] activate WakeOnLAN at device eth0
	ethtool -s eth0 wol g
else
	echo [WOL] WakeOnLAN is not enabled
fi
: exit 0
EOF
}

S = "${WORKDIR}"

inherit update-rc.d
INITSCRIPT_NAME = "ethwol"
INITSCRIPT_PARAMS = "stop 32 0 ."

do_install() {
    install -d ${D}/etc/init.d
    install -m 0755 ${WORKDIR}/ethwol.sh ${D}/etc/init.d/ethwol
}
