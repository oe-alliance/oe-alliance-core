require dreambox-dvb-modules.inc

PR = "${INC_PR}.1"

SRC_URI[modules.md5sum] = "023e2546f40cd9a9c7a7f968bfd86f7e"
SRC_URI[modules.sha256sum] = "a5479477232c89a93b5d327d77d2173399873ea9f6411f146ffa4f7456f9e9d7"

SRC_URI += "file://patch_modules.sh"

#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

INITSCRIPT_NAME = "patch_modules.sh"
INITSCRIPT_PARAMS = "start 03 S ."

inherit update-rc.d

do_install_append() {
	install -d ${D}${sysconfdir}/init.d/
	install -m 0755 ${WORKDIR}/patch_modules.sh ${D}${sysconfdir}/init.d/
}

FILES_${PN} += "${sysconfdir}/init.d/patch_modules.sh"
