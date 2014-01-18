SRCDATE = "20140116"

require gigablue-initrd.inc

inherit deploy
do_deploy() {
	if [ -e initrd.bin ]; then
	install -m 0644 initrd.bin ${DEPLOYDIR}/initrd.bin
	fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "0a527e2eb19634ed343151927055e7b3"
SRC_URI[sha256sum] = "4272bab5f149c6525d39ad1fba9182dc26962927aaa9b9adeab799d1a83cf11e"
