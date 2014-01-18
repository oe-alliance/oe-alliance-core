SRCDATE = "20140116"

require gigablue-initrd.inc

inherit deploy
do_deploy() {
	if [ -e initrd.bin ]; then
	install -m 0644 initrd.bin ${DEPLOYDIR}/initrd.bin
	fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "43f5e0e3328a80d415ff21128660b0c1"
SRC_URI[sha256sum] = "b1d2fc15a1daa31d849c6e037faa2451c9cb2b9bae9568588b7cc0411da71243"
