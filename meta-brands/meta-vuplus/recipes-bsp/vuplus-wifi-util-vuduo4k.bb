require vuplus-wifi-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

PV="18.1"
SRCDATE = "20210428"
SRCDATE_PR = "r0"

do_install:append() {
	install -d ${D}${sysconfdir}/udev
	install -m 0755 ${S}/bcmwifi_firmware.sh ${D}${sysconfdir}/udev/
	install -m 0755 ${S}/bcmwifi_drv.sh ${D}${sysconfdir}/udev/
}

SRC_URI[md5sum] = "3b13453ac6fa0d0812b747836ea50b7d"
SRC_URI[sha256sum] = "ff109e28acc4df39fcd9173d0816c899b2de6a875992d94f9097853764fd46af"
