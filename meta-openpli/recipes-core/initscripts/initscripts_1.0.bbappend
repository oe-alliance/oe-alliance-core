PRINC = "3"

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

SRC_URI += " \
	file://removeopkg.sh \
	"

do_install_append() {
	# umountnfs should run before network stops (which is at K40)
	ln -sf		../init.d/umountnfs.sh	${D}${sysconfdir}/rc6.d/K31umountnfs.sh
	ln -sf		../init.d/umountnfs.sh	${D}${sysconfdir}/rc0.d/K31umountnfs.sh
	#
	install -m 0755	${WORKDIR}/removeopkg.sh	${D}${sysconfdir}/init.d
	ln -sf		../init.d/removeopkg.sh		${D}${sysconfdir}/rcS.d/S59removeopkg.sh
}
