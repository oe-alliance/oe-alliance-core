INC_PR = "1"

require conf/license/openpli-gplv2.inc

inherit allarch

FILES_${PN} = "/usr/bin /etc"

SRC_URI = "\
	file://oscam.conf \
	file://oscam.server \
	file://oscam.srvid \
	file://oscam.user \
	file://oscam.ac \
	file://oscam.cert \
	file://oscam.dvbapi \
	file://oscam.guess \
	file://oscam.ird \
	file://oscam.pem \
	file://oscam.provid \
	file://oscam.services \
	file://oscam.tiers \
	"
S = "${WORKDIR}/${CAMNAME}"

# no docs, locales etc.
PACKAGES = "${PN}"

RDEPENDS_${PN} += "softcam-support"

CAMLINK = "/etc/init.d/softcam"
CAMPATH = "/etc/init.d/softcam.${CAMNAME}"

CAMSTART = "exec start-stop-daemon -S -x /usr/bin/${CAMNAME} -- -b -r 2 -c /etc/tuxbox/config/${CAMNAME}"
CAMSTOP ?= "exec start-stop-daemon -K -R 2 -x /usr/bin/${CAMNAME}"

CONFFILES = "/etc/tuxbox/config/${CAMNAME}/oscam.conf /etc/tuxbox/config/${CAMNAME}/oscam.server /etc/tuxbox/config/${CAMNAME}/oscam.srvid /etc/tuxbox/config/${CAMNAME}/oscam.user /etc/tuxbox/config/${CAMNAME}/oscam.ac /etc/tuxbox/config/${CAMNAME}/oscam.cert /etc/tuxbox/config/${CAMNAME}/oscam.dvbapi /etc/tuxbox/config/${CAMNAME}/oscam.guess /etc/tuxbox/config/${CAMNAME}/oscam.ird /etc/tuxbox/config/${CAMNAME}/oscam.pem /etc/tuxbox/config/${CAMNAME}/oscam.provid /etc/tuxbox/config/${CAMNAME}/oscam.services /etc/tuxbox/config/${CAMNAME}/oscam.tiers"

# Generate a simplistic standard init script
# (sorry for the sleep 1, but start-stop-daemon -R does not work as advertised)
do_compile () {
	if [ ! -f init.${CAMNAME} ]
	then
		cat > init.${CAMNAME} << EOF
#!/bin/sh

case "\$1" in
start)
	ulimit -s 512
	${CAMSTART}
	;;
stop)
	${CAMSTOP} &
	sleep 2
	killall -9 ${CAMNAME} 2>/dev/null
	sleep 2
	rm -rf /tmp/.oscam
	;;
restart|reload)
	\$0 stop
	sleep 1
	\$0 start
	;;
version)
	echo "${PV}"
	;;
info)
	echo "${CAMNAME}"
	;;
*)
	echo "Usage: \$0 start|stop|restart"
	exit 1
	;;
esac
exit 0
EOF

	fi
}

# Install routine, should be ok for most cams.
do_install_append () {
	install -d ${D}/etc/init.d
	install -m 755 ${S}/init.${CAMNAME} ${D}/etc/init.d/softcam.${CAMNAME}
}

# If no cam selected yet, install and start this cam (and don't start it on the build host).
pkg_postinst_append () {
	if [ ! -e ${CAMLINK} ] || [ "$D/etc/init.d/softcam.None" == `readlink -f $D${CAMLINK}` ]
	then
		rm -f $D${CAMLINK}
		ln -s softcam.${CAMNAME} $D${CAMLINK}
		echo "Switching default softcam to ${CAMNAME}"
	fi
	if [ "${CAMPATH}" == `readlink -f ${CAMLINK}` ]
	then
		echo "Softcam is selected as default, (re)starting ${CAMNAME}"
		${CAMPATH} restart
	fi
}

# Stop this cam (if running), and move softlink to None if we're the current cam
pkg_prerm_prepend () {
	${CAMPATH} stop
	OLDLINK=`readlink -f ${CAMLINK}`
	if [ "${OLDLINK}" == "${CAMPATH}" ]
	then
		echo "${CAMNAME} was selected, now selecting None as softcam"
		rm -f ${CAMLINK}
		ln -s softcam.None ${CAMLINK}
	fi
}

do_install() {
	install -d ${D}/etc/tuxbox/config/${CAMNAME}
	install -m 0644 ${WORKDIR}/oscam.* ${D}/etc/tuxbox/config/${CAMNAME}/
}
