require dreambox-dvb-modules.inc

PR = "${INC_PR}.2"

SRC_URI[modules.md5sum] = "c4421f27f70a67b2929cc29e790604aa"
SRC_URI[modules.sha256sum] = "224ceee0df07abb10522a2bc6479f11c953c7db12bbf8a0529c49971637256c7"

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
