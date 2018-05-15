STLINUX_FW_FILE_NAME = "stlinux24-sh4-stmfb-firmware-${PV}.noarch.rpm"
DESCRIPTION = "STLinux 2.4 STM Framebuffer firmware"

require stlinux24-sh4-fw.inc

SRC_URI[md5sum] = "a5b506d279aebba72bb2ac8436e9f8ff"
SRC_URI[sha256sum] = "f5ac77765ad6e3d747f76ae89f1377b7f4c2bcd4bc714048b453881f3d7dbc1b"

PR = "${INC_PR}.1"

do_install() {
        install -d ${D}${nonarch_base_libdir}/firmware
        install -m 0644 ${S}/lib/firmware/component_7105* ${D}${nonarch_base_libdir}/firmware
	ln -sf ${base_libdir}/firmware/component_7105_hdk7105.fw ${D}${nonarch_base_libdir}/firmware/component.fw
        install -m 0644 ${S}/lib/firmware/fdvo0_7105.fw ${D}${nonarch_base_libdir}/firmware/fdvo0.fw
	touch ${D}${nonarch_base_libdir}/firmware/downmix.fw
}
 
