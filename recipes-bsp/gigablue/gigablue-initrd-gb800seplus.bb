SRCDATE = "20140116"

require gigablue-initrd.inc

inherit deploy
do_deploy() {
	if [ -e initrd.bin ]; then
	install -m 0644 initrd.bin ${DEPLOYDIR}/initrd.bin
	fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "8ddfec851ec2783db77ccae054234ae8"
SRC_URI[sha256sum] = "ab9627bdfa493d919e89e4ad2bbf3f61eeb37c795e2c30eddee6f60eca24b084"
