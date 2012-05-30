PRINC = "2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

do_install_append() {
	# umountnfs should run before network stops (which is at K40)
	ln -sf		../init.d/umountnfs.sh	${D}${sysconfdir}/rc6.d/K31umountnfs.sh
	ln -sf		../init.d/umountnfs.sh	${D}${sysconfdir}/rc0.d/K31umountnfs.sh
}
