require vuplus-wifi-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

PV="18.1"
SRCDATE = "20181109"
SRCDATE_PR = "r0"

do_install_append() {
	install -d ${D}${sysconfdir}/udev
	install -m 0755 ${S}/bcmwifi_firmware.sh ${D}${sysconfdir}/udev/
	install -m 0755 ${S}/bcmwifi_drv.sh ${D}${sysconfdir}/udev/
}

SRC_URI[md5sum] = "2df0715a75c7ff0b85f13f907536bf84"
SRC_URI[sha256sum] = "a02c58fe339e6d75d7da9ec55d99520dff4838adec0ed60225ff4a85a7e5649d"
