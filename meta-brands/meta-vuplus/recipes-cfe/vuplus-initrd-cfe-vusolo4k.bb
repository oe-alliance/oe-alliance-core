SRCDATE = "20160229"

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7366b0 ]; then
    install -m 0644 vmlinuz-initrd-7366b0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "061ee14a4c87f0fca5ca7eae578d2454"
SRC_URI[sha256sum] = "b2dd96a85454bff1272db6628659aebc18cb7ad5bb001ce130bc9724f4b5ef79"
