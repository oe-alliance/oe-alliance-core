# To create a softcam:
# - install binary to /usr/softcams/
# - install other stuff wherever you need it
# - fill in (mandatory)
#   CAMNAME: Name of softcam, preferably matches /usr/softcams name
# - fill in (optional)
#   CAMSTART: Command to start the softcam, default to /usr/softcams/${CAMNAME}
#   CAMSTOP: Command to stop the softcam, default to killall ${CAMNAME}
# Instead of CAMSTART/CAMSTOP, you can create an init.d script called init.${CAMNAME}

FILES_${PN} = "/usr /etc"

# no docs, locales etc.
PACKAGES = "${PN}"

RDEPENDS_${PN} += "softcam-support"

CAMLINK = "/etc/init.d/softcam"
CAMPATH = "/etc/init.d/softcam.${CAMNAME}"

CAMSTART ?= "exec start-stop-daemon -S -x /usr/softcams/${CAMNAME}"
CAMSTOP  ?= "exec start-stop-daemon -K -R 2 -x /usr/softcams/${CAMNAME}"

#RRECOMMENDS_${PN} += "softcam-feed-universal"

# Generate a simplistic standard init script
# (sorry for the sleep 1, but start-stop-daemon -R does not work as advertised)
do_compile_append () {
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
#	[ "x$D" == "x" ] && [ -e "${CAMLINK}" ] && ${CAMLINK} stop 2>/dev/null || true
pkg_postinst_${PN}_append () {
	if [ ! -e "$D${CAMLINK}" ] || [ "$D${CAMLINK}.None" == "`readlink -f $D${CAMLINK}`" ]
	then
		rm -f "$D${CAMLINK}" 2>/dev/null
		ln -s "softcam.${CAMNAME}" "$D${CAMLINK}"
		echo "Switching default softcam to ${CAMNAME}"
	fi

	if [ "x$D" == "x" ]; then
		if [ "${CAMPATH}" == "`readlink -f ${CAMLINK}`" ]
		then
			echo "Softcam is selected as default, (re)starting ${CAMNAME}"
			${CAMPATH} stop 2>/dev/null || true
			sleep 2
			${CAMPATH} start || true
		fi
	fi
	exit 0
}

# Stop this cam (if running), and move softlink to None if we're the current cam
pkg_prerm_${PN}_prepend () {
	if [ "x$D" == "x" ]; then
		${CAMPATH} stop 2>/dev/null || true
	fi
	OLDLINK="`readlink -f $D${CAMLINK}`" 2>/dev/null
	if [ "${OLDLINK}" == "$D${CAMPATH}" -a -f $D${CAMLINK}.None ]
	then
		echo "${CAMNAME} was selected, now selecting None as softcam"
		rm -f "$D${CAMLINK}"
		ln -s "softcam.None" "$D${CAMLINK}"
	fi
	exit 0
}

INSANE_SKIP_${PN} += "already-stripped build-deps"
