require conf/license/openpli-gplv2.inc

FILES_${PN} = "/usr/bin /etc"

inherit allarch

# no docs, locales etc.
PACKAGES = "${PN}"

RDEPENDS_${PN} += "cardserver-support"

CSLINK = "/etc/init.d/cardserver"
CSPATH = "/etc/init.d/cardserver.${CSNAME}"

CSSTART ?= "exec start-stop-daemon -S -x /usr/bin/${CSNAME}"
CSSTOP  ?= "exec start-stop-daemon -K -R 2 -x /usr/bin/${CSNAME}"

# Generate a simplistic standard init script
# (sorry for the sleep 1, but start-stop-daemon -R does not work as advertised)
do_compile () {
	if [ ! -f init.${CSNAME} ]
	then
		cat > init.${CSNAME} << EOF
#!/bin/sh

remove_tmp () {
rm -rf /tmp/.oscam /media/usb/oscam/*
}\


case "\$1" in
start)
	remove_tmp
	sleep 3
	${CSSTART}
	exit
	;;
stop)
	${CSSTOP} &
	sleep 2
	killall -9 ${CSNAME} 2>/dev/null
	sleep 2
	remove_tmp
	sleep 2
	exit
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
	echo "${CSNAME}"
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
	install -m 755 ${S}/init.${CSNAME} ${D}/etc/init.d/cardserver.${CSNAME}
}

# If no cam selected yet, install and start this cam (and don't start it on the build host).
pkg_postinst_append () {
	if [ ! -e ${CSLINK} ] || [ "$D/etc/init.d/cardserver.None" == `readlink -f $D${CSLINK}` ]
	then
		rm -f $D${CSLINK}
		ln -s ${CSPATH} $D${CSLINK}
		echo "Switching default cardserver to ${CSNAME}"
	fi
	if [ "${CSPATH}" == `readlink -f ${CSLINK}` ]
	then
		echo "Cardserver is selected as default, (re)starting ${CSNAME}"
		${CSPATH} restart
	fi
}

# Stop this cam (if running), and move softlink to None if we're the current cam
pkg_prerm_prepend () {
	${CSPATH} stop
	OLDLINK=`readlink -f ${CSLINK}`
	if [ "${OLDLINK}" == "${CSPATH}" ]
	then
		echo "${CSNAME} was selected, now selecting None as cardserver"
		rm -f ${CSLINK}
		ln -s cardserver.None ${CSLINK}
	fi
}

