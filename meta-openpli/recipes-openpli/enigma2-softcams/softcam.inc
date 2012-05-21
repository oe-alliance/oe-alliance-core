# To create a softcam:
# - install binary to /usr/bin/
# - install other stuff wherever you need it
# - fill in (mandatory)
#   CAMNAME: Name of softcam, preferably matches /usr/bin name
# - fill in (optional)
#   CAMSTART: Command to start the softcam, default to /usr/bin/${CAMNAME}
#   CAMSTOP: Command to stop the softcam, default to killall ${CAMNAME}
# Instead of CAMSTART/CAMSTOP, you can create an init.d script called init.${CAMNAME}

LICENSE = "CLOSED"

FILES_${PN} = "/usr/bin /etc"

# no docs, locales etc.
PACKAGES = "${PN}"

RDEPENDS_${PN} += "softcam-support"

CAMLINK = "/etc/init.d/softcam"
CAMPATH = "/etc/init.d/softcam.${CAMNAME}"

CAMSTART ?= "exec start-stop-daemon -S -x /usr/bin/${CAMNAME}"
CAMSTOP  ?= "exec start-stop-daemon -K -R 2 -x /usr/bin/${CAMNAME}"

# Generate a simplistic standard init script
# (sorry for the sleep 1, but start-stop-daemon -R does not work as advertised)
do_compile () {
	if [ ! -f init.${CAMNAME} ]
	then
		cat > init.${CAMNAME} << EOF
#!/bin/sh
case "\$1" in
start)
	ulimit -s 1024
	${CAMSTART}
	;;
stop)
	${CAMSTOP}
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
	echo "${CAMNAME} ${PV}"
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

