SRCDATE = "20130220"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7425b0 ]; then
    install -m 0644 vmlinuz-initrd-7425b0 ${DEPLOYDIR}/initrd_cfe_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "90f22e35a63dca8591d2e21fef417377"
SRC_URI[sha256sum] = "48d612d8383113008569dd422a7cafa8d0f6b5c6e77772dd73f77724ede205c6"
