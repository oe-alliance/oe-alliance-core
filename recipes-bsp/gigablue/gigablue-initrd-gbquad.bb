SRCDATE = "20140116"

require gigablue-initrd.inc

inherit deploy
do_deploy() {
    if [ -e initrd.bin ]; then
    install -m 0644 initrd.bin ${DEPLOYDIR}/initrd.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "9b3abc17904625d23d4fe0deb3946cf4"
SRC_URI[sha256sum] = "5563545d47f69e5c3d9d69fb1fc6bc9411a4dba2c821be5e04ff696e2439311f"
