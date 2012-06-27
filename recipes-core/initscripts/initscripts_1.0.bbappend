PRINC = "1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

SRC_URI_append_gb800se = " \
	file://ddbootup \
	"

SRC_URI_append_gb800solo = " \
	file://ddbootup \
	"

SRC_URI_append_gb800ue = " \
	file://ddbootup \
	"
	
SRC_URI_append_gbquad = " \
	file://ddbootup \
	"
	
do_install_append_gb800se () {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${sysconfdir}/rcS.d
	install -m 0755    ${WORKDIR}/ddbootup	${D}${sysconfdir}/init.d
	ln -sf		../init.d/ddbootup	${D}${sysconfdir}/rcS.d/S06ddbootup
}

do_install_append_gb800ue () {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${sysconfdir}/rcS.d
	install -m 0755    ${WORKDIR}/ddbootup	${D}${sysconfdir}/init.d
	ln -sf		../init.d/ddbootup	${D}${sysconfdir}/rcS.d/S06ddbootup
}

do_install_append_gb800solo () {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${sysconfdir}/rcS.d
	install -m 0755    ${WORKDIR}/ddbootup	${D}${sysconfdir}/init.d
	ln -sf		../init.d/ddbootup	${D}${sysconfdir}/rcS.d/S06ddbootup
}

do_install_append_gbquad () {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${sysconfdir}/rcS.d
	install -m 0755    ${WORKDIR}/ddbootup	${D}${sysconfdir}/init.d
	ln -sf		../init.d/ddbootup	${D}${sysconfdir}/rcS.d/S06ddbootup
}