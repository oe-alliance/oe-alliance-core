DESCRIPTION = "Runs a shell in an environment as emitted by BitBake to execute tasks"
PR = "r3"

require conf/license/openpli-gplv2.inc

inherit autotools pkgconfig

do_configure() {
	:
}

def devshell_emit_env(o, d, all=False, funcwhitelist=None):
    """Emits all items in the data store in a format such that it can be sourced by a shell."""

    env = bb.data.keys(d)

    for e in env:
        if bb.data.getVarFlag(e, "func", d):
            continue
        bb.data.emit_var(e, o, d, all) and o.write('\n')

    for e in env:
        if not bb.data.getVarFlag(e, "func", d):
            continue
        if not funcwhitelist:
            bb.data.emit_var(e, o, d) and o.write('\n')
            continue
        for i in funcwhitelist:
            if e.startswith(i):
                bb.data.emit_var(e, o, d) and o.write('\n')
                break

python do_compile() {
	import os
	import os.path

	workdir = bb.data.getVar('WORKDIR', d, 1)
	shellfile = os.path.join(workdir, bb.data.expand("${TARGET_PREFIX}${DISTRO}-${MACHINE}-devshell", d))

	f = open(shellfile, "w")

	# emit variables and shell functions
        devshell_emit_env(f, d, False, ["die", "oe", "autotools_do_configure"])

	f.close()
}

do_install() {
	:
}

do_deploy() {
	shellfile="${TARGET_PREFIX}${DISTRO}-${MACHINE}-devshell"

	cd ${WORKDIR}

	cp $shellfile tmpfile
	echo "#!/bin/bash --rcfile" > $shellfile
	sed -e "s:${S}:.:g" -e "s:exit 1:true:" tmpfile >> $shellfile

	echo "export PS1='[OE::${TARGET_PREFIX}${DISTRO}-${MACHINE}]:\w\$ '" >> $shellfile
	echo "alias ./configure=oe_runconf" >> $shellfile
	echo "alias make=oe_runmake" >> $shellfile

	mkdir -p ${DEPLOY_DIR}/addons
	install -m 755 $shellfile ${DEPLOY_DIR}/addons
}

addtask deploy after do_install before do_package
