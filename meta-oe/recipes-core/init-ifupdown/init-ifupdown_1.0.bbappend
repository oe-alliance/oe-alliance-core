FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}/${DISTRO_NAME}:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${MACHINE}:"

PACKAGE_ARCH = "${MACHINE_ARCH}"
PR:append:openatv = ".2"
PR:append:openspa = ".1"
RDEPENDS:${PN}:append = "${@bb.utils.contains_any('DISTRO_NAME', 'openatv openspa', ' util-linux-flock', '', d)}"

INITSCRIPT_PARAMS = "start 10 2 3 4 5 . stop 80 0 6 1 ."

do_install:append () {
	install -d ${D}${datadir}/enigma2/defaults
	install -m 0644 ${UNPACKDIR}/interfaces ${D}${datadir}/enigma2/defaults/interfaces
    # Serialize the parallel boot/restart path with detached udev workers.
    # --close prevents DHCP/WPA daemons from inheriting the lock descriptor.
    if ${@bb.utils.contains_any('DISTRO_NAME','openatv openspa','true','false',d)}; then
        sed -i -e 's|^[[:space:]]*ifup -a|\tflock --close /run/oe-network.lock ifup -a|' \
               -e 's|^[[:space:]]*ifdown -a|\tflock --close /run/oe-network.lock ifdown -a|' \
            ${D}${sysconfdir}/init.d/networking
    fi
}

FILES:${PN} += " ${datadir}/enigma2/defaults"
