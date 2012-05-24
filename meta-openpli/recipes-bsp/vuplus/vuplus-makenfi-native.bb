DESCRIPTION = "create vuplus NAND images"
SECTION = "utils"
LICENSE = "CLOSED"

PV = "1.0"
PR = "r1"

SRC_URI = "file://vfi2 \
	   file://vfi3"

inherit native

do_compile() {
	cp ${WORKDIR}/vfi2 .
	cp ${WORKDIR}/vfi3 .
}

do_install() {
        install -m 0755 vfi2 ${STAGING_BINDIR}/
        install -m 0755 vfi3 ${STAGING_BINDIR}/
}

python sstate_task_postfunc () {
}
