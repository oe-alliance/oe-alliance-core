require vuplus-wifi-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

PV="18.1"
SRCDATE = "20201228"
SRCDATE_PR = "r0"

do_install_append() {
	install -d ${D}${sysconfdir}/udev
	install -m 0755 ${S}/bcmwifi_firmware.sh ${D}${sysconfdir}/udev/
	install -m 0755 ${S}/bcmwifi_drv.sh ${D}${sysconfdir}/udev/
}

SRC_URI[md5sum] = "72b9151b3fbf37df5071bfd9d2660263"
SRC_URI[sha256sum] = "1e3fad4fddb2ba71290a6cb73bd8b5645a206f106fb13e85c96c2da413deb96f"
