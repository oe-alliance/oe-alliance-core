SRCDATE = "20161214"

require dags-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e initrd_run.bin  ]; then
    install -m 0644 initrd_run.bin  ${DEPLOYDIR}/initrd_run.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "780144d3d4ee39dba3c1b112e42ae4ea"
SRC_URI[sha256sum] = "d07ce87cdb43b49cca8a3fb936d6b47c59be845f8aca5f05a1ef68b2590e05a7"
