KV = "3.13.5"
SRCDATE = "20190715"

require vuplus-dvb-modules.inc

SRC_URI[md5sum] = "d6de2bbb3dc4dadc6fbea49420464493"
SRC_URI[sha256sum] = "99132a0f1321970efc726e6c4fd71fc237865347d9dd4bf3e365e817226b0349"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 03 S ."
INITSCRIPT_NAME = "vuplus-dvb-modules"

SRC_URI = " \
	https://source.mynonpublic.com/vuplus/release/vuplus-dvb-modules/vuplus-dvb-modules-${MACHINE}-${PV}-${SRCDATE}.tar.gz \
	file://${INITSCRIPT_NAME}.sysvinit \
"

do_install() {
	install -d ${D}/lib/modules/${KV}/extra
	for f in *.ko; do
		install -m 0644 ${WORKDIR}/$f ${D}/lib/modules/${KV}/extra/$f;
	done

	if [ -f ${WORKDIR}/${INITSCRIPT_NAME}.sysvinit ]; then
		install -d ${D}${INIT_D_DIR}
		install -m 0755 ${WORKDIR}/${INITSCRIPT_NAME}.sysvinit ${D}${INIT_D_DIR}/${INITSCRIPT_NAME}
	fi
}

FILES:${PN} += "${INIT_D_DIR}/${INITSCRIPT_NAME}"